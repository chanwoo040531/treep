package me.chnu.treep.config.web

import me.chnu.treep.exception.UnauthorizedException
import me.chnu.treep.infrastructure.UserClient
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
internal class AuthUserHandlerArgumentResolver(
    private val userClient: UserClient,
) : HandlerMethodArgumentResolver {
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

        return userClient.get(authHeader)
    }
}