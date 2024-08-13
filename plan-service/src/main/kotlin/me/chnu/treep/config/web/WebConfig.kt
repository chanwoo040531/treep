package me.chnu.treep.config.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration(proxyBeanMethods = false)
internal class WebConfig(
    private val authUserHandlerArgumentResolver: AuthUserHandlerArgumentResolver,
    private val objectMapper: ObjectMapper,
) : WebMvcConfigurationSupport() {
    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.apply {
            add(authUserHandlerArgumentResolver)
        }
    }

    override fun extendMessageConverters(converters: List<HttpMessageConverter<*>>) =
        converters.forEach {
            if (it is MappingJackson2HttpMessageConverter) {
                it.objectMapper = objectMapper
                return@extendMessageConverters
            }
        }
}

