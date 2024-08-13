package me.chnu.treep.config.web

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.springframework.core.MethodParameter

class AuthUserHandlerArgumentResolverTest : ShouldSpec({
    val resolver = AuthUserHandlerArgumentResolver(mockk())

    context("supportsParameter") {
        val authUser = object {
            fun test(authUser: AuthUser) {}
        }
        val string = object {
            fun test(string: String) {}
        }

        should("return true if parameter is AuthUser") {
            val method = authUser.javaClass.getMethod("test", AuthUser::class.java)
            val methodParam = MethodParameter(method, 0)

            resolver.supportsParameter(methodParam) shouldBe true
        }

        should("return false if parameter is not AuthUser") {
            val method = string.javaClass.getMethod("test", String::class.java)
            val methodParam = MethodParameter(method, 0)

            resolver.supportsParameter(methodParam) shouldBe false
        }
    }
})