package me.chnu.treep.config.web

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Scheduler
import me.chnu.treep.exception.UnauthorizedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.concurrent.TimeUnit

@Component
internal class AuthUserHandlerArgumentResolver(
    @Value("\${service.auth.url}")
    private val authUrl: String,
) : HandlerMethodArgumentResolver {
    private val restClient: RestClient = RestClient.create()

    private val authUserCache: Cache<String, AuthUser> = Caffeine.newBuilder()
        .expireAfterWrite(20, TimeUnit.MINUTES)
        .scheduler(Scheduler.systemScheduler())
        .build()

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        AuthUser::class.java.isAssignableFrom(parameter.parameterType)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authHeader: String = webRequest.getHeader("Authorization")
            ?: throw UnauthorizedException()

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