package me.chnu.treep.domain.itinerary.entity

import jakarta.persistence.*
import me.chnu.treep.domain.BaseEntity
import me.chnu.treep.domain.plan.entity.Plan
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.ZonedDateTime

@Entity
@Table(name = "itineraries")
@EntityListeners(AuditingEntityListener::class)
internal class Itinerary(
    var title: String,
    var description: String,
    var cost: BigDecimal,
    var startAt: ZonedDateTime,
    var endAt: ZonedDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    val plan: Plan,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Itinerary? = null,
) : BaseEntity() {
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
}