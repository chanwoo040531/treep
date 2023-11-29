package me.chnu.treep.domain.itinerary.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.chnu.treep.domain.Key
import java.time.LocalDateTime

@Entity
@Table(name = "stay_itineraries")
internal class Stay(
    override val tripPlanId: Key,
    override var itineraryType: ItineraryType = ItineraryType.STAY,
    override var title: String,
    override var cost: Long,
    override var startAt: LocalDateTime,
    override var endAt: LocalDateTime,
    var accommodationName: String,
    var accommodationAddress: String,

    ) : Itinerary(
    tripPlanId = tripPlanId,
    itineraryType = itineraryType,
    title = title,
    cost = cost,
    startAt = startAt,
    endAt = endAt,
)