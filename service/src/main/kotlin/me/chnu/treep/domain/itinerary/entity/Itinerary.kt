package me.chnu.treep.domain.itinerary.entity

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Table
import me.chnu.treep.domain.BaseEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Entity
@Table(name = "itineraries")
@EntityListeners(AuditingEntityListener::class)
internal class Itinerary(
    val tripPlanId: Long,
    var title: String,
    var description: String,
    var cost: Long,
    var startAt: ZonedDateTime,
    var endAt: ZonedDateTime,
) : BaseEntity() {
    @CreatedDate
    lateinit var createdAt: ZonedDateTime

    @LastModifiedDate
    lateinit var lastUpdatedAt: ZonedDateTime
}