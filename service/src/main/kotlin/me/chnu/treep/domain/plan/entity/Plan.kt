package me.chnu.treep.domain.plan.entity

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import me.chnu.treep.domain.BaseEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@Entity
@Table(name = "plans")
@EntityListeners(AuditingEntityListener::class)
internal class Plan(
    var title: String,
    var startAt: ZonedDateTime,
    var endAt: ZonedDateTime,
    var budget: Double,
    @NotNull
    val userId: Long,
) : BaseEntity() {

    @CreatedDate
    lateinit var createdAt: ZonedDateTime

    @LastModifiedDate
    lateinit var lastUpdatedAt: ZonedDateTime
}