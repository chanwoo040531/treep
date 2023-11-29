package me.chnu.treep.domain.plan

import me.chnu.treep.annotation.WriteService
import me.chnu.treep.domain.Key
import me.chnu.treep.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull

@WriteService
internal class TripPlanWriteService(
    private val tripPlanRepository: TripPlanRepository,
) {
    fun create(planData: PlanData) = tripPlanRepository.save(planData.toTripPlan())
    fun update(planData: PlanData) {
        val plan = tripPlanRepository.findByIdOrNull(planData.userId)
            ?: throw NotFoundException("여행 계획을 찾을 수 없습니다")

        return with(plan) {
            title = planData.title
            startDate = planData.startDate
            endDate = planData.endDate
            budget = planData.budget

            PlanInfo.from(this)
        }
    }

    fun delete(planId: Key) {
        tripPlanRepository.deleteById(planId)
    }
}