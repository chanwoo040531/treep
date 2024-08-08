package me.chnu.treep.exception

import io.github.oshai.kotlinlogging.KotlinLogging
import me.chnu.treep.presentation.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
internal class ExceptionHandler {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException) =
        responseEntity(
            ex,
            HttpStatus.UNAUTHORIZED,
            ex.message
        )

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException) =
        responseEntity(
            ex,
            HttpStatus.NOT_FOUND,
            ex.message
        )

    @ExceptionHandler(ServerException::class)
    fun handleServerException(ex: ServerException) =
        responseEntity(
            ex,
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.message
        )

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception) =
        responseEntity(
            ex,
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal Server Error"
        ).also { logger.error { ex.message } }

    fun responseEntity(
        ex: Throwable,
        status: HttpStatusCode,
        message: String
    ): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity.status(status)
            .body(ApiResponse.error(message = message))
    }
}