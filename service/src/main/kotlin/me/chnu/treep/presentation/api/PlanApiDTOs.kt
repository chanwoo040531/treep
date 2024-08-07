package me.chnu.treep.presentation.api

import me.chnu.treep.domain.plan.entity.Plan
import java.time.ZonedDateTime

internal data class PlanRequest(
    val title: String,
    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime,
    val budget: Long,
) {
    fun toPlan(userId: Long) = Plan(
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
    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime,
    val budget: Long,
    val createdAt: ZonedDateTime,
    val lastUpdatedAt: ZonedDateTime,
) {
    companion object {
        fun from(plan: Plan) = with(plan) {
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

