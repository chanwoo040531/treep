package me.chnu.treep.domain.itinerary.entity

import jakarta.persistence.*
import me.chnu.treep.config.web.UserId
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@Entity
@Table(name = "itineraries")
@EntityListeners(AuditingEntityListener::class)
internal class Itinerary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var title: String,
    var startAt: ZonedDateTime,
    var endAt: ZonedDateTime,
    var budget: Double,
    val userId: UserId,
) {
    lateinit var createdAt: ZonedDateTime
    lateinit var lastUpdatedAt: ZonedDateTime

    @PrePersist
    fun prePersist() {
        createdAt = ZonedDateTime.now()
        lastUpdatedAt = ZonedDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        lastUpdatedAt = ZonedDateTime.now()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (this.javaClass != other?.javaClass) {
            return false
        }

        other as Itinerary

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}