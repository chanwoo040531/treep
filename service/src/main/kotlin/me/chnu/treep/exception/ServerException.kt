package me.chnu.treep.exception

import java.lang.RuntimeException

internal sealed class ServerException(
    override val message: String,
) : RuntimeException(message)

internal data class NotFoundException(
    override val message: String
)  : ServerException(message = message)

internal data class UnauthorizedException(
    override val message: String = "잘못된 인증 정보입니다"
)  : ServerException(message = message)

