package me.chnu.treep.domain.user

import com.auth0.jwt.interfaces.DecodedJWT
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

    /**
     * spring security 에서 UsernamePasswordAuthenticationFilter 를 사용한다고 했는데
     * 수동으로 검증을 구현한 이유가 있나요?
     * UsernamePasswordAuthenticationToken 를 통해 검증 했다면 아래와 같이 매치를 직접 구현하지 않아도 되었을텐데요
     */
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

            /**
             * 이 부분은 redis template 이 비즈니스 로직에 침투 하게 되었네요
             * redis 관련 해서 이 부분을 담당하는 컴포넌트 만든 뒤 관리해도 괜찮다고 생각해요
             * 그렇게 되면 아래 로직을 추상화하기에도 좋고 다른 data store 로 변경할 때
             * 하나의 컴포넌트만 바꾸면 되기때문에 더 좋지 않을까요?
             * 그렇지 않다면 redis template 을 사용하는 모든 component 들을 전부 바꿔줘야하겠네요.
             */
            redisTemplate.opsForValue().set(token, id.toString())
            redisTemplate.expire(token, Duration.ofMinutes(1L))

            AuthInfo(
                username = username,
                token = token,
            )
        }

    // 여기도 위와 마찬가지에요
    fun getByToken(token: JwtToken): UserInfo {
        val userId = if (redisTemplate.hasKey(token)) {
            val userId = redisTemplate.opsForValue().getAndExpire(token, 1L, TimeUnit.MINUTES) ?: throw InvalidJwtTokenException()
            // 취향 차이지만... userId as Long 으로 했으면 형변환이 한번만 일어났을거같네요
            val toLong = userId.toString().toLong()
            toLong
        } else {
            val decodedJWT = token.decode(jwtProperties.secret, jwtProperties.issuer)

            /**
             * 확장 변수를 만들어서 val DecodedJWT.userId 라는 변수를 만드는 것도 괜찮아보이네요. - 맨 아래 예시
             * decodedJWT.claims["userId"]?.asLong() 이부분은 비즈니스 로직에 올리지 않아도 괜찮지 않았을까 하네요.
             */
            val userId = decodedJWT.claims["userId"]?.asLong()

            redisTemplate.opsForValue().set(token, userId.toString())
            redisTemplate.expire(token, Duration.ofMinutes(1L))
            userId
        }  ?: throw InvalidJwtTokenException() // <- 띄어쓰기가 두번 들어갔는데 이런 부분은 조금 신경을 써주면 좀더 좋을거같아요 (의도 한걸수도 있지만 ㅋㅋ)

        /**
         * getByToken 안에 존재하기 때문에 어느정도 파악은 되지만
         * 다른 개발자들이 어떤것을 가져오는지 조금더 명확하게 파악할 수 있게 get() 이 아닌 다른 getUser 와 같은 네이밍을 했으면 좋았을거같네요.
         */
        return get(userId).let(UserInfo::from)
    }

    fun get(userId: Key): User =
        userRepository.findByIdOrNull(userId) ?: throw NotFoundException("유저를 찾을 수 없습니다")
}

// 예시
private val DecodedJWT.userId
    get() = this.claims["userId"]?.asLong()