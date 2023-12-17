package me.chnu.treep.exception

import java.lang.RuntimeException

// 매개변수가 message 하나일 땐 named argument 를 사용안해도 괜찮을거 같아요
internal sealed class ServerException(
    override val message: String,
) : RuntimeException(message)

internal data class NotFoundException(
    override val message: String
)  : ServerException(message = message)

internal data class UserAlreadyExistsException(
    override val message: String = "이미 존재하는 유저입니다"
)  : ServerException(message = message)

internal data class InvalidPasswordException(
    override val message: String = "유효하지 않은 비밀번호입니다"
)  : ServerException(message = message)

internal data class InvalidJwtTokenException(
    override val message: String = "유효하지 않은 토큰입니다"
)  : ServerException(message = message)
