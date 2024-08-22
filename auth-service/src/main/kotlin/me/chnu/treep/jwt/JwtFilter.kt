package me.chnu.treep.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.chnu.treep.util.JwtManager.decode
import me.chnu.treep.util.property.JwtProperties
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

internal class JwtFilter(
    private val jwtProperties: JwtProperties,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.getHeader("Authorization")?.let {
            if (it.startsWith("Bearer ")) {
                val accessToken = it.split(" ")[1]
                val jwtToken = JwtToken(accessToken)
                val decodedJWT = jwtToken.decode(jwtProperties.secret, jwtProperties.issuer)
                val username = decodedJWT.claims["username"]?.asString()
                    ?: throw IllegalArgumentException("username claim not found")

                val authentication = UsernamePasswordAuthenticationToken(username, null)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        filterChain.doFilter(request, response)
    }
}