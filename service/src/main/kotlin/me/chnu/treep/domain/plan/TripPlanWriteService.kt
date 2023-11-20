package me.chnu.treep.domain.plan

import me.chnu.treep.annotation.WriteService
import me.chnu.treep.domain.Key
import me.chnu.treep.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull

@WriteService
internal class TripPlanWriteService(
    private val tripPlanRepository: TripPlanRepository,
) {
    fun create(planInfo: PlanInfo) = tripPlanRepository.save(planInfo.toTripPlan())
    fun update(planInfo: PlanInfo) {
        val plan = tripPlanRepository.findByIdOrNull(planInfo.userId)
            ?: throw NotFoundException("여행 계획을 찾을 수 없습니다")

        return with(plan) {
            title = planInfo.title
            startDate = planInfo.startDate
            endDate = planInfo.endDate
            budget = planInfo.budget

            PlanDetailInfo.from(this)
        }
    }

    fun delete(planId: Key) {
        tripPlanRepository.deleteById(planId)
    }
}