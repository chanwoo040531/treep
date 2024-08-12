package me.chnu.treep.config.web

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.mockserver.MockServerListener
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import me.chnu.treep.exception.UnauthorizedException
import org.mockserver.client.MockServerClient
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest

class AuthUserHandlerArgumentResolverTest : ShouldSpec({

    val resolver =
        AuthUserHandlerArgumentResolver(
            "http://$MOCK_SERVER_HOST:$MOCK_SERVER_PORT$MOCK_SERVER_ENDPOINT"
        )

    listener(MockServerListener(MOCK_SERVER_PORT))

    beforeTest {
        MockServerClient(MOCK_SERVER_HOST, MOCK_SERVER_PORT).apply {
            `when`(
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath(MOCK_SERVER_ENDPOINT)
                    .withHeaders(
                        Header("Authorization", "Bearer available-token")
                    )
            ).respond(
                HttpResponse.response()
                    .withStatusCode(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                            "id": 1,
                            "username": "test",
                            "expiresAt": 1767225600000
                        }
                        """.trimIndent()
                    )
            )

            `when`(
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath(MOCK_SERVER_ENDPOINT)
                    .withHeaders(
                        Header("Authorization", "Bearer expired-token")
                    )
            ).respond(
                HttpResponse.response()
                    .withStatusCode(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                            "id": 1,
                            "username": "test",
                            "expiresAt": 0
                        }
                        """.trimIndent()
                    )
            )
        }
    }

    context("supportsParameter") {
        val authUser = object { fun test(authUser: AuthUser) {} }
        val string = object { fun test(string: String) {} }

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

    context("resolveArgument") {
        should("return AuthUser if Authorization header is valid") {
            val webRequest = mockkClass(NativeWebRequest::class)

            every { webRequest.getHeader("Authorization") } returns "Bearer available-token"

            val actual = resolver.resolveArgument(
                mockk(),
                null,
                webRequest,
                null
            )

            (actual is AuthUser) shouldBe true
            actual as AuthUser
            actual.userId shouldBe 1
            actual.username shouldBe "test"
            actual.expiresAt shouldBe 1767225600000
        }

        should("throw UnauthorizedException if cached AuthUser is expired") {
            val webRequest = mockkClass(NativeWebRequest::class)

            every { webRequest.getHeader("Authorization") } returns "Bearer expired-token"

            resolver.resolveArgument(
                mockk(),
                null,
                webRequest,
                null
            )

            shouldThrow<UnauthorizedException> {
                resolver.resolveArgument(
                    mockk(),
                    null,
                    webRequest,
                    null
                )
            }
        }
    }

}) {
    companion object {
        const val MOCK_SERVER_HOST = "localhost"
        const val MOCK_SERVER_PORT = 18302
        const val MOCK_SERVER_ENDPOINT = "/api/v1/users/info"
    }
}