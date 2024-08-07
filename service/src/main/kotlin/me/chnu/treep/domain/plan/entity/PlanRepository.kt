package me.chnu.treep.domain.plan.entity

import org.springframework.data.jpa.repository.JpaRepository

internal interface PlanRepository : JpaRepository<Plan, Long>