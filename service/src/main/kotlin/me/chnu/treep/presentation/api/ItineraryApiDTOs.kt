package me.chnu.treep.presentation.api

import me.chnu.treep.domain.itinerary.entity.Itinerary
import me.chnu.treep.domain.plan.entity.Plan
import java.math.BigDecimal
import java.time.ZonedDateTime

internal class ItineraryRequest(
    val parentId: Long?,
    private val title: String,
    private val cost: BigDecimal,
    private val startAt: ZonedDateTime,
    private val endAt: ZonedDateTime,
    private val description: String,
) {
    fun toItinerary(plan: Plan, parent: Itinerary?) = Itinerary(
        plan = plan,
        title = title,
        cost = cost,
        startAt = startAt,
        endAt = endAt,
        description = description,
        parent = parent,
    )
}

internal class ItineraryResponse(
    val id: Long,
    val title: String,
    val description: String,
    val cost: BigDecimal,
    val startAt: ZonedDateTime,
    val endAt: ZonedDateTime,
    val createdAt: ZonedDateTime,
    val lastUpdatedAt: ZonedDateTime,
) {
    companion object {
        fun from(itinerary: Itinerary) = with(itinerary) {
            ItineraryResponse(
                id = id,
                title = title,
                description = description,
                cost = cost,
                startAt = startAt,
                endAt = endAt,
                createdAt = createdAt,
                lastUpdatedAt = lastUpdatedAt,
            )
        }
    }
}
