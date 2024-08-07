package me.chnu.treep.domain.itinerary

import me.chnu.treep.annotation.ReadService
import me.chnu.treep.domain.itinerary.entity.Itinerary
import me.chnu.treep.domain.itinerary.entity.ItineraryRepository
import org.springframework.data.repository.findByIdOrNull

@ReadService
internal class ItineraryReadService(
    private val itineraryRepository: ItineraryRepository
) {
    fun getAll(planId: Long): List<Itinerary> =
        itineraryRepository.findAllByTripPlanId(planId)

    fun get(itineraryId: Long): Itinerary =
        itineraryRepository.findByIdOrNull(itineraryId)
            ?: throw IllegalArgumentException("일정이 존재하지 않습니다")
}