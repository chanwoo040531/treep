package me.chnu.treep.presentation.api

import me.chnu.treep.domain.user.AuthData
import me.chnu.treep.domain.user.AuthInfo
import me.chnu.treep.domain.user.UserInfo
import me.chnu.treep.jwt.AccessToken
import java.time.LocalDateTime

internal data class SignUpRequest(
    val username: String,
    val password: String,
) {
    fun toAuthData() = AuthData(
        username = username,
        password = password,
    )
}

internal data class SignInRequest(
    val username: String,
    val password: String,
) {
    fun toAuthData() = AuthData(
        username = username,
        password = password,
    )
}

internal data class SignInResponse(
    val username: String,
    val token: AccessToken,
) {
    companion object {
        fun from(authInfo: AuthInfo) = SignInResponse(
            username = authInfo.username,
            token = authInfo.token,
        )
    }
}

internal data class UserResponse(
    val userId: Long,
    val username: String,
    val password: String,
    val updatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(info: UserInfo) = UserResponse(
            userId = info.userId,
            username = info.username,
            password = info.password,
            updatedAt = info.updatedAt,
            createdAt = info.createdAt,
        )
    }
}