package me.chnu.treep.presentation.api

import me.chnu.treep.domain.itinerary.entity.Itinerary
import me.chnu.treep.domain.plan.entity.Plan
import java.math.BigDecimal
import java.time.ZonedDateTime

internal class ItineraryRequest(
    private val title: String,
    private val cost: BigDecimal,
    private val startAt: ZonedDateTime,
    private val endAt: ZonedDateTime,
    private val description: String,
) {
    fun toItinerary(plan: Plan) = Itinerary(
        plan = plan,
        title = title,
        cost = cost,
        startAt = startAt,
        endAt = endAt,
        description = description,
    )
}

internal class ItineraryResponse(
    val id: Long,
    val title: String,
    val cost: BigDecimal,
    val startAt: ZonedDateTime,
    val endAt: ZonedDateTime,
) {
    companion object {
        fun from(itinerary: Itinerary) = with(itinerary) {
            ItineraryResponse(
                id = id,
                title = title,
                cost = cost,
                startAt = startAt,
                endAt = endAt,
            )
        }
    }
}
