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

// 사용하지 않는 의존성은 제거하고 후행 콤마를 넣어 보시는게 어떨까요?
@WriteService
internal class UserWriteService(
    private val userRepository: UserRepository,
    private val redisTemplate: RedisTemplate<String, Any>
) {
    // 아래처럼 사용하면 확실히 가독성, 명확성이 좋네요
    // 재사용성을 봤을 땐 find method 가 맞지만 명시성과 성능을 봤을 땐 exists 도 "개인적" 으로 좋다고 생각이 드네요
    fun signup(authData: AuthData) = with(authData) {
        userRepository.findByUsername(username)
            ?.run { throw UserAlreadyExistsException() }

        userRepository.save(authData.toUser())
    }

    fun logout(token: JwtToken) = redisTemplate.delete(token)
}