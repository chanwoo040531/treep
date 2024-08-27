package me.chnu.treep.domain.user

import org.springframework.data.jpa.repository.JpaRepository

internal interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean
}