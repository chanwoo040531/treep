package me.chnu.treep.domain.plan

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.chnu.treep.domain.BaseEntity
import me.chnu.treep.domain.Key
import java.time.LocalDateTime

@Entity
@Table(name = "trip_plans")
internal class TripPlan(
    var title: String,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,
    var budget: Long,
    val userId: Key,
) : BaseEntity()