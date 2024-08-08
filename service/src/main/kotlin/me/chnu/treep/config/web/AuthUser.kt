package me.chnu.treep.config.web

import com.fasterxml.jackson.annotation.JsonProperty

internal interface AuthUser {
    @get:JsonProperty("id")
    val userId: Long
    val username: String
    val expiresAt: Long
}