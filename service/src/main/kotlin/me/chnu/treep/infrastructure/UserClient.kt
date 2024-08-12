package me.chnu.treep.infrastructure

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Scheduler
import me.chnu.treep.annotation.Client
import me.chnu.treep.config.web.AuthUser
import me.chnu.treep.exception.UnauthorizedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import java.util.concurrent.TimeUnit

@Client
internal class UserClient(
    @Value("\${service.auth.url}")
    private val authUrl: String,
) {
    private val restClient: RestClient = RestClient.create()

    private val authUserCache: Cache<String, AuthUser> = Caffeine.newBuilder()
        .expireAfterWrite(20, TimeUnit.MINUTES)
        .scheduler(Scheduler.systemScheduler())
        .build()

    fun get(authHeader: String): AuthUser {
        authUserCache.getIfPresent(authHeader)?.let {
            if (it.expiresAt > System.currentTimeMillis()) {
                return it
            }
            throw UnauthorizedException()
        }

        return authUserCache.get(authHeader) {
            restClient
                .get()
                .uri(authUrl)
                .header("Authorization", authHeader)
                .retrieve()
                .onError(HttpStatus.INTERNAL_SERVER_ERROR, RuntimeException())
                .onError(HttpStatus.UNAUTHORIZED, UnauthorizedException())
                .body<AuthUser>()
                ?: throw UnauthorizedException()
        }
    }

    private fun RestClient.ResponseSpec.onError(
        httpStatus: HttpStatusCode,
        throwable: Throwable
    ): RestClient.ResponseSpec =
        this.onStatus({ it == httpStatus }, { _, _ -> throw throwable })
}