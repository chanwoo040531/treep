package me.chnu.treep.domain.plan

import me.chnu.treep.annotation.WriteService
import me.chnu.treep.domain.plan.entity.TripPlan
import me.chnu.treep.domain.plan.entity.TripPlanRepository

@WriteService
internal class TripPlanWriteService(
    private val tripPlanRepository: TripPlanRepository,
) {
    fun create(plan: TripPlan): TripPlan = tripPlanRepository.save(plan)
}