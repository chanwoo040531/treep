package me.chnu.treep.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
@Transactional(readOnly = false)
internal annotation class UseCase(@get:AliasFor(annotation = Component::class) val value: String = "")

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
@Transactional(readOnly = true)
internal annotation class ReadService(@get:AliasFor(annotation = Component::class) val value: String = "")

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
@Transactional(readOnly = false)
internal annotation class WriteService(@get:AliasFor(annotation = Component::class) val value: String = "")

