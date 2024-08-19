package me.chnu.treep.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import me.chnu.treep.jwt.JwtToken
import me.chnu.treep.util.property.JwtProperties
import java.util.*

internal object JwtManager {
    fun createToken(claim: JwtClaim, properties: JwtProperties): JwtToken =
        JWT.create()
            .withIssuer(properties.issuer)
            .withSubject(properties.subject)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + properties.expiresTime * 1000))
            .withClaim("userId", claim.userId)
            .withClaim("username", claim.username)
            .sign(Algorithm.HMAC256(properties.secret))
            .let { JwtToken(it) }

    fun JwtToken.decode(secret: String, issuer: String): DecodedJWT =
        JWT.require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .build()
            .verify(this.value)
}

internal data class JwtClaim(
    val userId: Long,
    val username: String,
)