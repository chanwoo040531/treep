package me.chnu.treep.util.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

// @ConstructorBinding 이건 써먹어야겠네요 ㅋㅋ
@ConfigurationProperties(prefix = "jwt")
internal data class JwtProperties @ConstructorBinding constructor(
    val issuer: String,
    val subject: String,
    val expiresTime: Long,
    val secret: String,
)