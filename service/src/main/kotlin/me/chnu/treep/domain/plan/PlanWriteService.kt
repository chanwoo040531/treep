package me.chnu.treep.domain.plan

import me.chnu.treep.annotation.WriteService
import me.chnu.treep.domain.plan.entity.Plan
import me.chnu.treep.domain.plan.entity.PlanRepository
import me.chnu.treep.exception.NotFoundException
import me.chnu.treep.presentation.api.PlanRequest

@WriteService
internal class PlanWriteService(
    private val planRepository: PlanRepository,
) {
    fun create(plan: Plan): Plan = planRepository.save(plan)

    fun update(planId: Long, request: PlanRequest): Unit =
        planRepository.findById(planId)
            .orElseThrow { throw NotFoundException("Plan not found") }
            .apply {
                title = request.title
                startAt = request.startAt
                endAt = request.endAt
                budget = request.budget
            }
            .let { planRepository.save(it) }

    fun delete(planId: Long): Unit = planRepository.deleteById(planId)
}