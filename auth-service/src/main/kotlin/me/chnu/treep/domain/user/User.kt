package me.chnu.treep.domain.user

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.chnu.treep.domain.BaseEntity
import me.chnu.treep.domain.Key
import me.chnu.treep.util.Password
import java.time.LocalDateTime

@Entity
@Table(name = "users")
internal class User(
    var username: String,
    var password: Password,
) : BaseEntity()