package me.chnu.treep.util

import at.favre.lib.crypto.bcrypt.BCrypt

typealias Password = String

object EncryptManager {
    fun String.encrypt(): Password = BCrypt.withDefaults().hashToString(12, this.toCharArray())

    fun Password.verify(hashedPassword: Password) = BCrypt.verifyer()
        .verify(this.toCharArray(), hashedPassword).verified
}