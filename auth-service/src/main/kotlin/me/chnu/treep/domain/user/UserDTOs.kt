package me.chnu.treep.domain.user

import me.chnu.treep.domain.Key
import me.chnu.treep.util.EncryptManager.encrypt
import me.chnu.treep.util.JwtToken
import java.time.LocalDateTime

internal data class AuthData(
    val username: String,
    val password: String,
) {
    fun toUser() = User(
        username = username,
        password = password.encrypt()
    )
}

internal data class AuthInfo(
    val username: String,
    val token: JwtToken
)

internal data class UserInfo(
    val userId: Key,
    val username: String,
    val password: String,
    val updatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(user: User) = UserInfo(
            userId = user.id,
            username = user.username,
            password = user.password,
            updatedAt = user.updatedAt!!,
            createdAt = user.createdAt!!,
        )
    }
}