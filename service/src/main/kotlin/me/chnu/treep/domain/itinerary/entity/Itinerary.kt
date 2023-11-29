package me.chnu.treep.domain.itinerary.entity

import jakarta.persistence.*
import me.chnu.treep.domain.BaseEntity
import me.chnu.treep.domain.Key
import java.time.LocalDateTime

@Entity
@Table(name = "itineraries")
@Inheritance(strategy = InheritanceType.JOINED)
internal open class Itinerary(
    open val tripPlanId: Key,

    @Enumerated(value = EnumType.STRING)
    open var itineraryType: ItineraryType,

    open var title: String,

    open var cost: Long,

    open var startAt: LocalDateTime,

    open var endAt: LocalDateTime,
) : BaseEntity()