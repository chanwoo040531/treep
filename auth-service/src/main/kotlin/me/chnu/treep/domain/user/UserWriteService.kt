package me.chnu.treep.domain.user

import me.chnu.treep.annotation.WriteService
import me.chnu.treep.exception.UserAlreadyExistsException
import me.chnu.treep.util.JwtClaim
import me.chnu.treep.util.JwtManager
import me.chnu.treep.util.JwtManager.decode
import me.chnu.treep.util.JwtToken
import me.chnu.treep.util.property.JwtProperties
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.client.RestTemplate
import java.time.Duration
import java.util.concurrent.TimeUnit

@WriteService
internal class UserWriteService(
    private val userRepository: UserRepository,
    private val redisTemplate: RedisTemplate<String, Any>
) {
    fun signup(authData: AuthData) = with(authData) {
        userRepository.findByUsername(username)
            ?.run { throw UserAlreadyExistsException() }

        userRepository.save(authData.toUser())
    }

    fun logout(token: JwtToken) = redisTemplate.delete(token)
}