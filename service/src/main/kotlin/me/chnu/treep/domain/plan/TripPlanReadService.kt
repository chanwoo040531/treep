package me.chnu.treep.domain.plan

import me.chnu.treep.annotation.ReadService
import me.chnu.treep.domain.plan.entity.TripPlan
import me.chnu.treep.domain.plan.entity.TripPlanRepository
import me.chnu.treep.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull

@ReadService
internal class TripPlanReadService(
    private val tripPlanRepository: TripPlanRepository,
) {
    fun getAll(): List<TripPlan> =
        tripPlanRepository.findAll()

    fun get(planId: Long): TripPlan =
        tripPlanRepository.findByIdOrNull(planId)
            ?: throw NotFoundException("여행 계획을 찾을 수 없습니다")
}
