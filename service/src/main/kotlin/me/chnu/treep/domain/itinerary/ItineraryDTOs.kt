package me.chnu.treep.domain.itinerary

import me.chnu.treep.domain.Key
import me.chnu.treep.domain.itinerary.entity.Dining
import me.chnu.treep.domain.itinerary.entity.Itinerary
import me.chnu.treep.domain.itinerary.entity.ItineraryType
import me.chnu.treep.domain.itinerary.entity.Stay
import me.chnu.treep.exception.NotFoundException
import me.chnu.treep.exception.ServerException
import java.time.LocalDateTime

internal interface ItineraryData{
    val tripPlanId: Key
    val title: String
    val cost: Long
    val startAt: LocalDateTime
    val endAt: LocalDateTime

    fun toItinerary(): Itinerary
}

internal data class DiningData(
    override val tripPlanId: Key,
    override val title: String,
    override val cost: Long,
    override val startAt: LocalDateTime,
    override val endAt: LocalDateTime,
    val restaurantName: String,
    val restaurantAddress: String,
): ItineraryData {
    override fun toItinerary() = Dining(
        tripPlanId = tripPlanId,
        title = title,
        cost = cost, 
        startAt = startAt,
        endAt = endAt,
        restaurantName = restaurantName,
        restaurantAddress = restaurantAddress,
    )
}

internal class StayData(
    override val tripPlanId: Key,
    override val title: String,
    override val cost: Long,
    override val startAt: LocalDateTime,
    override val endAt: LocalDateTime,
    val accommodationName: String,
    val accommodationAddress: String
): ItineraryData {
    override fun toItinerary() = Stay(
        tripPlanId = tripPlanId,
        title = title,
        cost = cost,
        startAt = startAt,
        endAt = endAt,
        accommodationName = accommodationName,
        accommodationAddress = accommodationAddress,
    )
}

internal interface ItineraryInfo {
    val id: Key
    val itineraryType: ItineraryType
    val tripPlanId: Key
    val title: String
    val cost: Long
    val startAt: LocalDateTime
    val endAt: LocalDateTime

    companion object {
        fun from(itinerary: Itinerary): ItineraryInfo {
            val itineraryInfo = when (itinerary) {
                is Dining -> DiningInfo.from(itinerary)
                is Stay -> StayInfo.from(itinerary)
                else -> throw NotFoundException("올바르지 않은 일정입니다")
            }
            return itineraryInfo
        }
    }
}

internal data class DiningInfo(

    override val id: Key,
    override val tripPlanId: Key,
    override val itineraryType: ItineraryType,
    override val title: String,
    override val cost: Long,
    override val startAt: LocalDateTime,
    override val endAt: LocalDateTime,
    val restaurantName: String,
    val restaurantAddress: String,
): ItineraryInfo {
    companion object {
        fun from(dining: Dining) = with(dining) {
            DiningInfo(
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

internal data class StayInfo(
    override val id: Key,
    override val tripPlanId: Key,
    override val itineraryType: ItineraryType,
    override val title: String,
    override val cost: Long,
    override val startAt: LocalDateTime,
    override val endAt: LocalDateTime,
    val accommodationName: String,
    val accommodationAddress: String,
): ItineraryInfo {
    companion object {
        fun from(stay: Stay): ItineraryInfo = with(stay) {
            StayInfo(
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