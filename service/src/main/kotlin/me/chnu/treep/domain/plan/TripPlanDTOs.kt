package me.chnu.treep.domain.plan

import me.chnu.treep.domain.Key
import java.time.LocalDateTime

internal data class PlanData(
    val userId: Key,
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val budget: Long,
) {
    fun toTripPlan() = TripPlan(
        userId = this.userId,
        title = this.title,
        startDate = this.startDate,
        endDate = this.endDate,
        budget = this.budget,
    )
}

internal data class PlanInfo(
    val id: Key,
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val budget: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    /**
     * 지금 시점의 response 에서 createdAt, updatedAt 은 String Type 으로 가져가도 괜찮았을거같아요.
     * BaseEntity 의 createdAt, updatedAt 을 생성자에 넣지 않고 property 로 뺀 뒤 지연 초기화를 해도 좋았을것 같네요
     *     @CreatedDate
     *     open lateinit var createdAt: LocalDateTime
     *
     *     @LastModifiedDate
     *     open lateinit var updatedAt: LocalDateTime
     *
     *     단언 연산자를 최대한 지양하는 편이 좋지 않을까요?
     */
    companion object {
        fun from(tripPlan: TripPlan) = with(tripPlan) {
            PlanInfo(
                id = id,
                title = title,
                startDate = startDate,
                endDate = endDate,
                budget = budget,
                createdAt = createdAt!!,
                updatedAt = updatedAt!!,
            )
        }
    }
}