package me.chnu.treep.domain.user

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.chnu.treep.domain.BaseEntity
import me.chnu.treep.util.Password

@Entity
@Table(name = "users")
internal class User(
    var username: String,
    var password: Password,
) : BaseEntity()