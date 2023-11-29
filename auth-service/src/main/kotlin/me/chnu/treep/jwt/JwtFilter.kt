package me.chnu.treep.jwt

import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.chnu.treep.domain.user.UserReadService
import me.chnu.treep.util.JwtManager
import me.chnu.treep.util.JwtManager.decode
import me.chnu.treep.util.property.JwtProperties
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.filter.OncePerRequestFilter

internal class JwtFilter(
    private val jwtProperties: JwtProperties,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenHeader = request.getHeader("Authorization")

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            val jwtToken = tokenHeader.split(" ")[1]
            val decodedJWT = jwtToken.decode(jwtProperties.secret, jwtProperties.issuer)
            val username = decodedJWT.claims["username"]?.asString()
            val user = User(username, "", listOf(SimpleGrantedAuthority("ROLE_USER")))
            val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}