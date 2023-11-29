package me.chnu.treep.domain.itinerary.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.chnu.treep.domain.Key
import java.time.LocalDateTime

@Entity
@Table(name = "dining_itineraries")
internal class Dining(
    override val tripPlanId: Key,
    override var itineraryType: ItineraryType = ItineraryType.DINING,
    override var title: String,
    override var cost: Long,
    override var startAt: LocalDateTime,
    override var endAt: LocalDateTime,
    var restaurantName: String,
    var restaurantAddress: String,

    ) : Itinerary(
    tripPlanId = tripPlanId,
    itineraryType = itineraryType,
    title = title,
    cost = cost,
    startAt = startAt,
    endAt = endAt,
)