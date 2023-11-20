package me.chnu.treep.domain.plan

import me.chnu.treep.domain.Key
import org.springframework.data.jpa.repository.JpaRepository

internal interface TripPlanRepository : JpaRepository<TripPlan, Key>