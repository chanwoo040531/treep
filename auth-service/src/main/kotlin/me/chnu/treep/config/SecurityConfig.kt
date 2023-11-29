package me.chnu.treep.config

import me.chnu.treep.jwt.JwtFilter
import me.chnu.treep.util.property.JwtProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
internal class SecurityConfig(
    private val jwtProperties: JwtProperties,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/api/v1/users/signup",
                    "/api/v1/users/signin",
                ).permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(JwtFilter(jwtProperties), UsernamePasswordAuthenticationFilter::class.java)
            .build()
}