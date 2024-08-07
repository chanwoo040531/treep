package me.chnu.treep.domain.plan

import me.chnu.treep.annotation.ReadService
import me.chnu.treep.domain.plan.entity.Plan
import me.chnu.treep.domain.plan.entity.PlanRepository
import me.chnu.treep.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull

@ReadService
internal class PlanReadService(
    private val planRepository: PlanRepository,
) {
    fun getAll(): List<Plan> =
        planRepository.findAll()

    fun get(planId: Long): Plan =
        planRepository.findByIdOrNull(planId)
            ?: throw NotFoundException("여행 계획을 찾을 수 없습니다")
}
