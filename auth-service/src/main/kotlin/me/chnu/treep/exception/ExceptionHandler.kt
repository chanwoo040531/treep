package me.chnu.treep.exception

import io.github.oshai.kotlinlogging.KotlinLogging
import me.chnu.treep.presentation.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
internal class ExceptionHandler {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(ServerException::class)
    fun handleServerException(ex: ServerException): ResponseEntity<ApiResponse<Unit>> {
        logger.error { ex.message }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error(message = ex.message))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error(message = "Internal Server Error"))
    }
}