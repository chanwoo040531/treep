package me.chnu.treep.presentation.api

import me.chnu.treep.domain.itinerary.entity.Itinerary
import java.time.LocalDateTime
import java.time.ZonedDateTime

internal class ItineraryRequest(
    private val tripPlanId: Long,
    private val title: String,
    private val cost: Long,
    private val startAt: ZonedDateTime,
    private val endAt: ZonedDateTime,
    private val description: String,
) {
    fun toItinerary() = Itinerary(
        tripPlanId = tripPlanId,
        title = title,
        cost = cost,
        startAt = startAt,
        endAt = endAt,
        description = description,
    )
}

internal class ItineraryResponse(
    val id: Long,
    val tripPlanId: Long,
    val title: String,
    val cost: Long,
    val startAt: ZonedDateTime,
    val endAt: ZonedDateTime,
) {
    companion object {
        fun from(itinerary: Itinerary) = with(itinerary) {
            ItineraryResponse(
                id = id,
                tripPlanId = tripPlanId,
                title = title,
                cost = cost,
                startAt = startAt,
                endAt = endAt,
            )
        }
    }
}
