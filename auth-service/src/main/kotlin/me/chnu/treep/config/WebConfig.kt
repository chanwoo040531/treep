package me.chnu.treep.config

import me.chnu.treep.annotation.AuthToken
import me.chnu.treep.jwt.JwtToken
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
internal class WebConfig(
    private val authTokenResolver: AuthTokenResolver,
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(resolvers)
        resolvers.add(authTokenResolver)
    }
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST" , "PUT", "DELETE")
            .maxAge(3600L)
    }
}

@Component
class AuthTokenResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthToken::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authHeader = webRequest.getHeader("Authorization")
        checkNotNull(authHeader)

        // Authorization: Bearer <token>
        val token = authHeader.split(" ")[1]
        return JwtToken(token)
    }

}