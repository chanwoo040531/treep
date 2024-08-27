package me.chnu.treep.domain.user

import me.chnu.treep.jwt.AccessToken
import me.chnu.treep.util.EncryptManager.encrypt
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
    val token: AccessToken
)

internal data class UserInfo(
    val userId: Long,
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