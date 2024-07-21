package me.chnu.treep.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import me.chnu.treep.util.property.JwtProperties
import java.util.*

typealias JwtToken = String

internal object JwtManager {
    // 뒤에 단언 연산자 붙히라고 노란줄 그이네요... ㅋㅋㅋ
    fun createToken(claim: JwtClaim, properties: JwtProperties) =
        JWT.create()
            .withIssuer(properties.issuer)
            .withSubject(properties.subject)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + properties.expiresTime * 1000))
            .withClaim("userId", claim.userId)
            .withClaim("username", claim.username)
            .sign(Algorithm.HMAC256(properties.secret))

    fun JwtToken.decode(secret: String, issuer: String) =
        JWT.require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .build()
            .verify(this)
}

internal data class JwtClaim(
    val userId: Long,
    val username: String,
)