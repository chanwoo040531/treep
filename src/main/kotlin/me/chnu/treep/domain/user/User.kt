package me.chnu.treep.domain.user

import jakarta.persistence.Entity
import jakarta.persistence.Id
import me.chnu.treep.common.BaseEntity

@Entity
class User (
    var name: String,
    var email: String,
): BaseEntity()