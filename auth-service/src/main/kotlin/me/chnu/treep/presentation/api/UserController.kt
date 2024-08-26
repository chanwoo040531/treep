package me.chnu.treep.presentation.api

import me.chnu.treep.annotation.AuthToken
import me.chnu.treep.domain.user.UserReadService
import me.chnu.treep.domain.user.UserWriteService
import me.chnu.treep.jwt.AccessToken
import me.chnu.treep.presentation.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
internal class UserController(
    private val userWriteService: UserWriteService,
    private val userReadService: UserReadService,
) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignUpRequest): ResponseEntity<Unit> {
        userWriteService.signup(request.toAuthData())

        return ResponseEntity.noContent().build()
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody request: SignInRequest): ResponseEntity<ApiResponse<SignInResponse>> {
        val response = userReadService.signIn(request.toAuthData()).let(SignInResponse::from)

        return ResponseEntity.ok(ApiResponse.success(response))
    }


    @DeleteMapping("/logout")
    fun logout(@AuthToken token: AccessToken) =
        userWriteService.logout(token)

    @GetMapping("/info")
    fun get(@AuthToken token: AccessToken): ResponseEntity<ApiResponse<UserResponse>> {
        val response = userReadService.getByToken(token).let(UserResponse::from)

        return ResponseEntity.ok(ApiResponse.success(response))
    }

}