package me.chnu.treep.domain.itinerary.entity

aimport jakarta.persistence.*
import me.chnu.treep.domain.BaseEntity
import me.chnu.treep.domain.plan.entity.Plan
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@Entity
@Table(name = "itineraries")
@EntityListeners(AuditingEntityListener::class)
internal class Itinerary(
    var title: String,
    var description: String,
    var cost: Double,
    var startAt: ZonedDateTime,
    var endAt: ZonedDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    val plan: Plan,

) : BaseEntity() {
    @CreatedDate
    lateinit var createdAt: ZonedDateTime

    @LastModifiedDate
    lateinit var lastUpdatedAt: ZonedDateTime
}