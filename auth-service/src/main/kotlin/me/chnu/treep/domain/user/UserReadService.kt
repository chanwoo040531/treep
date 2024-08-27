package me.chnu.treep.domain.user

import me.chnu.treep.annotation.ReadService
import me.chnu.treep.exception.InvalidJwtTokenException
import me.chnu.treep.exception.NotFoundException
import me.chnu.treep.jwt.AccessToken
import me.chnu.treep.util.JwtManager.decode
import me.chnu.treep.util.property.JwtProperties
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import java.time.Duration
import java.util.concurrent.TimeUnit

@ReadService
internal class UserReadService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val userRepository: UserRepository,
    private val jwtProperties: JwtProperties,
) : UserDetailsService {
    fun getByUsername(username: String): User {
        return userRepository.findByUsername(username)
            ?: throw NotFoundException("유저를 찾을 수 없습니다")
    }

    fun getByToken(token: AccessToken): UserInfo {
        val userId = if (redisTemplate.hasKey(token.value)) {
            val userId = redisTemplate.opsForValue().getAndExpire(token.value, 1L, TimeUnit.MINUTES)
                ?: throw InvalidJwtTokenException()
            val toLong = userId.toString().toLong()
            toLong
        } else {
            val decodedJWT = token.decode(jwtProperties.secret, jwtProperties.issuer)

            val userId = decodedJWT.claims["userId"]?.asLong()

            redisTemplate.opsForValue().set(token.value, userId.toString())
            redisTemplate.expire(token.value, Duration.ofMinutes(1L))
            userId
        } ?: throw InvalidJwtTokenException()

        return get(userId).let(UserInfo::from)
    }

    fun get(userId: Long): User =
        userRepository.findByIdOrNull(userId)
            ?: throw NotFoundException("유저를 찾을 수 없습니다")

    override fun loadUserByUsername(username: String): UserDetails {
        val user = getByUsername(username)

        return object : UserDetails {
            override fun getAuthorities() = listOf(GrantedAuthority { "ROLE_USER" })
            override fun isEnabled() = true
            override fun getUsername() = user.username
            override fun isCredentialsNonExpired() = true
            override fun getPassword() = user.password
            override fun isAccountNonExpired() = true
            override fun isAccountNonLocked() = true
        }
    }
}