package me.chnu.treep.domain.itinerary

import me.chnu.treep.domain.Key
import me.chnu.treep.domain.itinerary.entity.DiningItinerary
import java.time.LocalDateTime
import kotlin.math.cos

internal data class DiningInfo(
    val tripPlanId: Key,
    val title: String,
    val cost: Long,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val location: String,
    val restaurantName: String,
    val restaurantAddress: String,
) {
    fun toDiningItinerary() = DiningItinerary(
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
