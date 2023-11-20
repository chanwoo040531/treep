package me.chnu.treep

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class TreepApplication

fun main(args: Array<String>) {
    runApplication<TreepApplication>(*args)
}
