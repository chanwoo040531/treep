package me.chnu.treep.domain.user

import me.chnu.treep.annotation.WriteService
import me.chnu.treep.exception.NotFoundException
import me.chnu.treep.exception.UserAlreadyExistsException
import me.chnu.treep.jwt.AccessToken
import me.chnu.treep.util.JwtClaim
import me.chnu.treep.util.JwtManager
import me.chnu.treep.util.property.JwtProperties
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.userdetails.UserDetails
import java.time.Duration

@WriteService
internal class UserWriteService(
    private val userRepository: UserRepository,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val jwtProperties: JwtProperties,
    private var authenticationManagerBuilder: AuthenticationManagerBuilder,
) {
    fun signup(authData: AuthData): User = with(authData) {
        val exists = userRepository.existsByUsername(username)
        if (exists) {
            throw UserAlreadyExistsException()
        }
        validateUsername(username)
        validatePassword(password)

        userRepository.save(authData.toUser())
    }

    fun signIn(authData: AuthData): AuthInfo {
        userRepository.findByUsername(authData.username)
            ?: throw NotFoundException("유저 정보가 존재하지 않습니다")

        val jwtClaim = JwtClaim(
            username = authData.username,
        )

        val token = UsernamePasswordAuthenticationToken(authData.username, authData.password)
        val userAuth: UserDetails =
            authenticationManagerBuilder.getObject().authenticate(token).principal as UserDetails

        val accessToken: AccessToken = JwtManager.createToken(jwtClaim, jwtProperties)

        redisTemplate.opsForValue().set(accessToken.value, authData.username)
        redisTemplate.expire(accessToken.value, Duration.ofMinutes(jwtProperties.expiresTime))

        return AuthInfo(
            username = authData.username,
            token = accessToken,
        )
    }

    fun logout(token: AccessToken): Boolean = redisTemplate.delete(token.value)

    private fun validateUsername(username: String) = username.also {
        if (it.length < 4) {
            throw IllegalArgumentException("이메일은 4자 이상이어야 합니다.")
        } else if (it.length > 320) {
            throw IllegalArgumentException("이메일은 320자 이하여야 합니다.")
        } else if (!it.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) {
            throw IllegalArgumentException("이메일 형식이 올바르지 않습니다.")
        }
    }

    private fun validatePassword(password: String) = password.also {
        if (it.length < 8) {
            throw IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.")
        } else if (it.length > 20) {
            throw IllegalArgumentException("비밀번호는 20자 이하여야 합니다.")
        } else if (!it.matches(Regex(".*[0-9].*"))) {
            throw IllegalArgumentException("비밀번호는 숫자를 포함해야 합니다.")
        } else if (!it.matches(Regex(".*[a-zA-Z].*"))) {
            throw IllegalArgumentException("비밀번호는 영문자를 포함해야 합니다.")
        } else if (!it.matches(Regex(".*[!@#\$%^&*].*"))) {
            throw IllegalArgumentException("비밀번호는 특수문자를 포함해야 합니다.")
        }
    }
}