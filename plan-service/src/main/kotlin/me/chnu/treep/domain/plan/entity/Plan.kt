package me.chnu.treep.domain.plan.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import me.chnu.treep.domain.BaseEntity
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.ZonedDateTime

@Entity
@Table(name = "plans")
@EntityListeners(AuditingEntityListener::class)
internal class Plan(
    var title: String,
    var startAt: ZonedDateTime,
    var endAt: ZonedDateTime,
    var budget: BigDecimal,
    @NotNull
    val userId: Long,
) : BaseEntity() {
    lateinit var createdAt: ZonedDateTime
    lateinit var lastUpdatedAt: ZonedDateTime

    @PrePersist
    fun prePersist() {
        createdAt = ZonedDateTime.now()
        lastUpdatedAt = ZonedDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        lastUpdatedAt = ZonedDateTime.now()
    }
}