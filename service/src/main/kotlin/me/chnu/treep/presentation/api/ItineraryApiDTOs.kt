package me.chnu.treep.presentation.api

import me.chnu.treep.domain.Key
import me.chnu.treep.domain.itinerary.*
import me.chnu.treep.domain.itinerary.DiningData
import me.chnu.treep.domain.itinerary.ItineraryData
import me.chnu.treep.domain.itinerary.ItineraryInfo
import me.chnu.treep.domain.itinerary.StayData
import me.chnu.treep.domain.itinerary.entity.Dining
import me.chnu.treep.domain.itinerary.entity.Itinerary
import me.chnu.treep.domain.itinerary.entity.ItineraryType
import me.chnu.treep.domain.itinerary.entity.Stay
import java.time.LocalDateTime

internal interface ItineraryRequest{
    val tripPlanId: Key
    val title: String
    val cost: Long
    val startAt: LocalDateTime
    val endAt: LocalDateTime
    fun toItineraryData(tripPlanId: Key): ItineraryData
}

internal data class DiningRequest(
    override val tripPlanId: Key,
    override val title: String,
    override val cost: Long,
    override val startAt: LocalDateTime,
    override val endAt: LocalDateTime,
    val restaurantName: String,
    val restaurantAddress: String,
) : ItineraryRequest {
    override fun toItineraryData(tripPlanId: Key) = DiningData(
        tripPlanId = tripPlanId,
        title = title,
        cost = cost,
        startAt = startAt,
        endAt = endAt,
        restaurantName = restaurantName,
        restaurantAddress = restaurantAddress,
    )
}

internal data class StayRequest(
    override val tripPlanId: Key,
    override val title: String,
    override val cost: Long,
    override val startAt: LocalDateTime,
    override val endAt: LocalDateTime,
    val accommodationName: String,
    val accommodationAddress: String,
) : ItineraryRequest {
    override fun toItineraryData(tripPlanId: Key) = StayData(
        tripPlanId = tripPlanId,
        title = title,
        cost = cost,
        startAt = startAt,
        endAt = endAt,
        accommodationName = accommodationName,
        accommodationAddress = accommodationAddress,
    )
}

internal interface ItineraryResponse {
    val id: Key
    val itineraryType: ItineraryType
    val tripPlanId: Key
    val title: String
    val cost: Long
    val startAt: LocalDateTime
    val endAt: LocalDateTime

    companion object {
        fun from(itinerary: ItineraryInfo) = when (itinerary.itineraryType) {
            ItineraryType.DINING -> DiningResponse.from(itinerary as DiningInfo)
            ItineraryType.STAY -> StayResponse.from(itinerary as StayInfo)
        }
    }
}

internal data class DiningResponse(
    override val id: Key,
    override val tripPlanId: Key,
    override val itineraryType: ItineraryType,
    override val title: String,
    override val cost: Long,
    override val startAt: LocalDateTime,
    override val endAt: LocalDateTime,
    val restaurantName: String,
    val restaurantAddress: String,
): ItineraryResponse {
    companion object {
        fun from(dining: DiningInfo) = with(dining) {
            DiningResponse(
                id = id,
                tripPlanId = tripPlanId,
                itineraryType = itineraryType,
                title = title,
                cost = cost,
                startAt = startAt,
                endAt = endAt,
                restaurantName = restaurantName,
                restaurantAddress = restaurantAddress,
            )
        }
    }
}

internal data class StayResponse(
    override val id: Key,
    override val tripPlanId: Key,
    override val itineraryType: ItineraryType,
    override val title: String,
    override val cost: Long,
    override val startAt: LocalDateTime,
    override val endAt: LocalDateTime,
    val accommodationName: String,
    val accommodationAddress: String,
): ItineraryResponse {
    companion object {
        fun from(stay: StayInfo): ItineraryResponse = with(stay) {
            StayResponse(
                id = id,
                tripPlanId = tripPlanId,
                itineraryType = itineraryType,
                title = title,
                cost = cost,
                startAt = startAt,
                endAt = endAt,
                accommodationName = accommodationName,
                accommodationAddress = accommodationAddress,
            )
        }
    }
}

