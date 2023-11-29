package me.chnu.treep.domain.user

import me.chnu.treep.domain.Key
import org.springframework.data.jpa.repository.JpaRepository

internal interface UserRepository : JpaRepository<User, Key> {
    fun findByUsername(username: String): User?
}