package me.chnu.treep.annotation

import jakarta.servlet.http.HttpServletRequest
import me.chnu.treep.config.web.Role
import me.chnu.treep.exception.UnauthorizedException
import me.chnu.treep.infrastructure.UserClient
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoginRequired(vararg val hasRole : Role = [Role.USER])

@Aspect
@Component
internal class LoginRequiredAspect(
    private val userClient: UserClient,
    private val request: HttpServletRequest,
) {

    @Before("@annotation(me.chnu.treep.annotation.LoginRequired) && execution(* me.chnu.treep.presentation.api.*.*(..))")
    fun checkLogin(joinPoint: JoinPoint, loginRequired: LoginRequired) {
        val authHeader = request.getHeader("Authorization")
            ?: throw UnauthorizedException()

        val user = userClient.get(authHeader)
    }
}