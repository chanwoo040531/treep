package me.chnu.treep.util

import java.net.URI

fun String.toURI(): URI = URI.create(this)