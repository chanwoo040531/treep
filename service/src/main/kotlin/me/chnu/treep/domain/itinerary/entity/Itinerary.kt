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

@Entity
@Table(name = "itineraries")
@EntityListeners(AuditingEntityListener::class)
internal class Itinerary(
    val tripPlanId: Long,
    var title: String,
    var description: String,
    var cost: Long,
    var startAt: LocalDateTime,
    var endAt: LocalDateTime,
) : BaseEntity() {
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var lastUpdatedAt: LocalDateTime
}