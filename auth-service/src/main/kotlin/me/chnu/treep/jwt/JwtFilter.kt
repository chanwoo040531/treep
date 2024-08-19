package me.chnu.treep.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.chnu.treep.util.JwtManager.decode
import me.chnu.treep.util.property.JwtProperties
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.filter.OncePerRequestFilter

// 사용하지 않는 의존성(import)은 지우는게 좋아보여요
internal class JwtFilter(
    private val jwtProperties: JwtProperties,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        /**
         * tokenHeader 는 nullable 한 값이 아닌데 아래왜 같이 != null 로 검증한 이유가 있나요?
         * val tokenHeader: String? = request.getHeader("Authorization") 이렇게 위 로직 변경하면 좋을거같네요
         * header 에 Authorization 가 없을 수도 있으니까요.
         * 개인적으로는 request.getHeader("Authorization") 이 부분은 확장 변수로 처리해도 깔끔할거같아요. ex) request.accessToken
         * 지금과 같은 비즈니스 로직에 최대한 리터럴을 지양해보는건 어떨까요?
         */
        val tokenHeader = request.getHeader("Authorization")

        /**
         * 변수명을 보면 파악이 가능하지만 메서드 명으로도 파악할 수 있게 노력해보는 것도 좋을거같아요.
         */
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            val jwtToken = JwtToken(tokenHeader.split(" ")[1])
            val decodedJWT = jwtToken.decode(jwtProperties.secret, jwtProperties.issuer)
            val username = decodedJWT.claims["username"]?.asString()
            /**
             * 아마 저번에 이부분은 시간 부족으로 못했다고 한거같긴한데...
             * userEntity 가 JwtFilter 에 존재하지 않아도 될거같네요.
             */
            val user = User(username, "", listOf(SimpleGrantedAuthority("ROLE_USER")))
            val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}