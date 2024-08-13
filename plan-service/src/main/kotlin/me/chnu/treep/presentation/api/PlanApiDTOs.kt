package me.chnu.treep.presentation.api

import me.chnu.treep.domain.plan.entity.Plan
import java.math.BigDecimal
import java.time.ZonedDateTime

internal data class PlanRequest(
    val title: String,
    val startAt: ZonedDateTime,
    val endAt: ZonedDateTime,
    val budget: BigDecimal,
) {
    fun toPlan(userId: Long) = Plan(
        userId = userId,
        title = this.title,
        startAt = this.startAt,
        endAt = this.endAt,
        budget = this.budget,
    )
}

internal data class PlanResponse(
    val id: Long,
    val title: String,
    val startAt: ZonedDateTime,
    val endAt: ZonedDateTime,
    val budget: BigDecimal,
    val createdAt: ZonedDateTime,
    val lastUpdatedAt: ZonedDateTime,
) {
    companion object {
        fun from(plan: Plan) = with(plan) {
            PlanResponse(
                id = id,
                title = title,
                startAt = startAt,
                endAt = endAt,
                budget = budget,
                createdAt = createdAt,
                lastUpdatedAt = lastUpdatedAt,
            )
        }
    }
}

