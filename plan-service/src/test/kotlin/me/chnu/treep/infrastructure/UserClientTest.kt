package me.chnu.treep.infrastructure

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.mockserver.MockServerListener
import io.kotest.matchers.shouldBe
import me.chnu.treep.exception.UnauthorizedException
import org.mockserver.client.MockServerClient
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse

class UserClientTest : ShouldSpec({
    val userClient = UserClient("http://$MOCK_SERVER_HOST:$MOCK_SERVER_PORT$MOCK_SERVER_ENDPOINT")

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

    context("resolveArgument") {
        should("return AuthUser if Authorization header is valid") {
            val accessToken = "Bearer available-token"
            val actual = userClient.get(accessToken)

            actual.userId shouldBe 1
        }

        should("return AuthUser from cache if Authorization header is valid") {
            val accessToken = "Bearer available-token"
            userClient.get(accessToken)

            val actual = userClient.get(accessToken)

            actual.userId shouldBe 1
        }

        should("throw UnauthorizedException if cached AuthUser is expired") {
            val accessToken = "Bearer expired-token"
            userClient.get(accessToken)

            shouldThrow<UnauthorizedException> {
                userClient.get(accessToken)
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
