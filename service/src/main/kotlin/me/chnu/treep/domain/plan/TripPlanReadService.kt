package me.chnu.treep.domain.plan

import me.chnu.treep.annotation.ReadService
import me.chnu.treep.domain.Key
import me.chnu.treep.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull

@ReadService
internal class TripPlanReadService(
    private val tripPlanRepository: TripPlanRepository,
) {
    fun getAll(): List<PlanInfo> = tripPlanRepository.findAll().map(PlanInfo::from)
    fun get(planId: Key): PlanInfo =
        tripPlanRepository.findByIdOrNull(planId)
            ?.let(PlanInfo::from)
            ?: throw NotFoundException("여행 계획을 찾을 수 없습니다")
}
