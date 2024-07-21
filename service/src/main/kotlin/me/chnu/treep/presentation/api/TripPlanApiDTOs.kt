package me.chnu.treep.presentation.api

import me.chnu.treep.domain.plan.entity.TripPlan
import java.time.LocalDateTime

internal data class PlanRequest(
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val budget: Long,
) {
    fun toPlan(userId: Long) = TripPlan(
        userId = userId,
        title = this.title,
        startDate = this.startDate,
        endDate = this.endDate,
        budget = this.budget,
    )
}

internal data class PlanResponse(
    val id: Long,
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val budget: Long,
    val createdAt: LocalDateTime,
    val lastUpdatedAt: LocalDateTime,
) {
    companion object {
        fun from(tripPlan: TripPlan) = with(tripPlan) {
            PlanResponse(
                id = id,
                title = title,
                startDate = startDate,
                endDate = endDate,
                budget = budget,
                createdAt = createdAt,
                lastUpdatedAt = lastUpdatedAt,
            )
        }
    }
}

