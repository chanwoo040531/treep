package me.chnu.treep.exception

import java.lang.RuntimeException

internal sealed class ServerException(
    override val message: String,
) : RuntimeException(message)

internal data class NotFoundException(
    override val message: String
)  : ServerException(message = message)