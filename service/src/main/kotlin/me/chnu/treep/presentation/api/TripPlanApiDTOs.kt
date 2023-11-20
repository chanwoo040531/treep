package me.chnu.treep.presentation.api

import me.chnu.treep.domain.Key
import me.chnu.treep.domain.plan.PlanInfo
import me.chnu.treep.domain.plan.PlanDetailInfo
import java.time.LocalDateTime

internal data class PlanRequest(
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val budget: Long,
) {
    fun toPlanInfo(userId: Key) = PlanInfo(
        userId = userId,
        title = this.title,
        startDate = this.startDate,
        endDate = this.endDate,
        budget = this.budget,
    )
}

internal data class PlanResponse(
    val id: Key,
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val budget: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(planDetailInfo: PlanDetailInfo) = with(planDetailInfo) {
            PlanResponse(
                id = id,
                title = title,
                startDate = startDate,
                endDate = endDate,
                budget = budget,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}

