package me.chnu.treep.domain.itinerary.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import me.chnu.treep.domain.Key
import java.time.LocalDateTime

@Entity
@DiscriminatorValue("DINING")
internal class DiningItinerary(
    override val tripPlanId: Key,
    override var title: String,
    override var cost: Long,
    override var startAt: LocalDateTime,
    override var endAt: LocalDateTime,
    override var location: String,
    var restaurantName: String,
    var restaurantAddress: String,

    ) : Itinerary(
    tripPlanId =tripPlanId,
    title = title,
    cost = cost,
    startAt = startAt,
    endAt = endAt,
    location = location,
)