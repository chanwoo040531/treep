package me.chnu.treep.util.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "jwt")
internal data class JwtProperties @ConstructorBinding constructor(
    val issuer: String,
    val subject: String,
    val expiresTime: Long,
    val secret: String,
)