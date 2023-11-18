package me.chnu.treep

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TreepApplication

fun main(args: Array<String>) {
    runApplication<TreepApplication>(*args)
}
