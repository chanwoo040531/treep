package me.chnu.treep.presentation.api

import me.chnu.treep.application.user.UserWriteService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController(private val userWriteService: UserWriteService) {

    @PostMapping("signup")
    fun signup() {

    }
}