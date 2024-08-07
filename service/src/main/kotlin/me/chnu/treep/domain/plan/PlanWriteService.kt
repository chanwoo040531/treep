package me.chnu.treep.domain.plan

import me.chnu.treep.annotation.WriteService
import me.chnu.treep.domain.plan.entity.Plan
import me.chnu.treep.domain.plan.entity.PlanRepository

@WriteService
internal class PlanWriteService(
    private val planRepository: PlanRepository,
) {
    fun create(plan: Plan): Plan = planRepository.save(plan)
}