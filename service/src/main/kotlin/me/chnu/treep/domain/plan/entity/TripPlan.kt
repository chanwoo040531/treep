package me.chnu.treep.domain.plan.entity

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Table
import me.chnu.treep.domain.BaseEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "trip_plans")
@EntityListeners(AuditingEntityListener::class)
internal class TripPlan(
    var title: String,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,
    var budget: Long,
    val userId: Long,
) : BaseEntity() {

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var lastUpdatedAt: LocalDateTime
}