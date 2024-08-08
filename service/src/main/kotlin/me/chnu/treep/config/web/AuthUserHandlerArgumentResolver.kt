package me.chnu.treep.config.web

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Scheduler
import me.chnu.treep.exception.UnauthorizedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.MethodParameter
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
    @Value("\${service.auth.url}") val authUrl: String,
) : HandlerMethodArgumentResolver {

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
//            ?: throw UnauthorizedException()
        ?: "Bearer eyJhbGciOiJIUzI1NiJ9"

        authUserCache.getIfPresent(authHeader)?.let {
            if (it.expiresAt < System.currentTimeMillis()) {
                return it
            }
            authUserCache.invalidate(authHeader)
        }

        return authUserCache.get(authHeader) {
            RestClient.create()
                .get()
                .uri(authUrl)
                .header("Authorization", authHeader)
                .retrieve()
                .body<AuthUser>()
                ?: throw UnauthorizedException()
        }
    }
}