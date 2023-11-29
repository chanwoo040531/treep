package me.chnu.treep.domain.user

import me.chnu.treep.annotation.ReadService
import me.chnu.treep.domain.Key
import me.chnu.treep.exception.InvalidJwtTokenException
import me.chnu.treep.exception.InvalidPasswordException
import me.chnu.treep.exception.NotFoundException
import me.chnu.treep.util.EncryptManager.verify
import me.chnu.treep.util.JwtClaim
import me.chnu.treep.util.JwtManager
import me.chnu.treep.util.JwtManager.decode
import me.chnu.treep.util.JwtToken
import me.chnu.treep.util.property.JwtProperties
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.repository.findByIdOrNull
import java.time.Duration
import java.util.concurrent.TimeUnit

@ReadService
internal class UserReadService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val jwtProperties: JwtProperties,
    private val userRepository: UserRepository,
) {

    fun signIn(authData: AuthData) =
        with(userRepository.findByUsername(authData.username)
            ?: throw NotFoundException("유저 정보가 존재하지 않습니다")) {

            if (!authData.password.verify(password)) {
                throw InvalidPasswordException()
            }
            val jwtClaim = JwtClaim(
                userId = id,
                username = username,
            )

            val token: JwtToken = JwtManager.createToken(jwtClaim, jwtProperties)

            redisTemplate.opsForValue().set(token, id.toString())
            redisTemplate.expire(token, Duration.ofMinutes(1L))

            AuthInfo(
                username = username,
                token = token,
            )
        }

    fun getByToken(token: JwtToken): UserInfo {
        val userId = if (redisTemplate.hasKey(token)) {
            val userId = redisTemplate.opsForValue().getAndExpire(token, 1L, TimeUnit.MINUTES) ?: throw InvalidJwtTokenException()
            val toLong = userId.toString().toLong()
            toLong
        } else {
            val decodedJWT = token.decode(jwtProperties.secret, jwtProperties.issuer)
            val userId = decodedJWT.claims["userId"]?.asLong()
            redisTemplate.opsForValue().set(token, userId.toString())
            redisTemplate.expire(token, Duration.ofMinutes(1L))
            userId
        }  ?: throw InvalidJwtTokenException()

        return get(userId).let(UserInfo::from)
    }

    fun get(userId: Key): User =
        userRepository.findByIdOrNull(userId) ?: throw NotFoundException("유저를 찾을 수 없습니다")
}