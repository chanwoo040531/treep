package me.chnu.treep.config

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration(proxyBeanMethods = false)
internal class WebConfig(
    private val authUserHandlerArgumentResolver: AuthUserHandlerArgumentResolver,
    private val objectMapper: ObjectMapper,
)  : WebMvcConfigurationSupport() {
    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.apply {
            add(authUserHandlerArgumentResolver)
        }
    }

    override fun extendMessageConverters(converters: List<HttpMessageConverter<*>>) =
        converters.forEach {
            if (it is MappingJackson2HttpMessageConverter) {
                it.objectMapper = objectMapper
                return
            }
        }
}

@Component
internal class AuthUserHandlerArgumentResolver(
    @Value("\${service.auth.url}") val authUrl: String
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        AuthUser::class.java.isAssignableFrom(parameter.parameterType)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        return AuthUser(userId = 1, username = "tester@test.com")

//        val authHeader: String = webRequest.getHeader("Authorization")
//            ?: throw UnauthorizedException()
//
//        return runBlocking {
//            WebClient.create()
//                .get()
//                .uri(authUrl)
//                .header("Authorization", authHeader)
//                .retrieve()
//                .awaitBody<AuthUser>()
//        }
    }
}

data class AuthUser (
    @JsonProperty("id")
    val userId: Long,
    val username: String,
)