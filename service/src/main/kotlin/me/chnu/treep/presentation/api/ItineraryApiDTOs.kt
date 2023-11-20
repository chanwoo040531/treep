package me.chnu.treep.presentation.api

import me.chnu.treep.domain.Key
import me.chnu.treep.domain.itinerary.DiningInfo
import me.chnu.treep.domain.itinerary.entity.DiningItinerary
import java.time.LocalDateTime

internal data class DiningRequest(
    val title: String,
    val cost: Long,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val location: String,
    val restaurantName: String,
    val restaurantAddress: String,
) {
    fun toDiningInfo(tripPlanId: Key) = DiningInfo(
        tripPlanId = tripPlanId,
        title = title,
        cost = cost,
        startAt = startAt,
        endAt = endAt,
        location = location,
        restaurantName = restaurantName,
        restaurantAddress = restaurantAddress,
    )
}