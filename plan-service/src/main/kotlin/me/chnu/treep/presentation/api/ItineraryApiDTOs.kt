package me.chnu.treep.presentation.api

import me.chnu.treep.config.web.UserId
import me.chnu.treep.domain.itinerary.entity.Itinerary
import java.time.ZonedDateTime

internal data class ItineraryRequest(
    val title: String,
    val startAt: ZonedDateTime,
    val endAt: ZonedDateTime,
    val budget: Double,
) {
    fun toItinerary(userId: UserId) = Itinerary(
        userId = userId,
        title = this.title,
        startAt = this.startAt,
        endAt = this.endAt,
        budget = this.budget,
    )
}

internal data class ItineraryResponse(
    val id: Long,
    val title: String,
    val startAt: ZonedDateTime,
    val endAt: ZonedDateTime,
    val budget: Double,
    val createdAt: ZonedDateTime,
    val lastUpdatedAt: ZonedDateTime,
) {
    companion object {
        fun from(itinerary: Itinerary) = with(itinerary) {
            ItineraryResponse(
                id = id,
                title = title,
                startAt = startAt,
                endAt = endAt,
                budget = budget,
                createdAt = createdAt,
                lastUpdatedAt = lastUpdatedAt,
            )
        }
    }
}

