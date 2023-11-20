package me.chnu.treep.domain.plan

import me.chnu.treep.domain.Key
import java.time.LocalDateTime

internal data class PlanInfo(
    val userId: Key,
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val budget: Long,
) {
    fun toTripPlan() = TripPlan(
        userId = this.userId,
        title = this.title,
        startDate = this.startDate,
        endDate = this.endDate,
        budget = this.budget,
    )
}

internal data class PlanDetailInfo(
    val id: Key,
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val budget: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(tripPlan: TripPlan) = with(tripPlan) {
            PlanDetailInfo(
                id = id,
                title = title,
                startDate = startDate,
                endDate = endDate,
                budget = budget,
                createdAt = createdAt!!,
                updatedAt = updatedAt!!,
            )
        }
    }
}