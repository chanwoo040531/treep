package me.chnu.treep.domain.itinerary.entity

import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table
import me.chnu.treep.domain.BaseEntity
import me.chnu.treep.domain.Key
import java.time.LocalDateTime

@Entity
@Table(name = "itineraries")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "itinerary_type")
internal open class Itinerary(
    open val tripPlanId: Key,
    open var title: String,
    open var cost: Long,
    open var startAt: LocalDateTime,
    open var endAt: LocalDateTime,
    open var location: String
) : BaseEntity()