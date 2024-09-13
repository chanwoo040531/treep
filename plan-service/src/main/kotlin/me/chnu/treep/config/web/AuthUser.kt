package me.chnu.treep.config.web

import com.fasterxml.jackson.annotation.JsonProperty

@JvmInline
value class UserId(val value: Long)

internal data class AuthUser(
    @get:JsonProperty("id")
    val userId: UserId,
    val username: String,
    val expiresAt: Long,
    val roles: List<Role>,
)

enum class Role {
    USER,
    ADMIN
}