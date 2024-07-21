package me.chnu.treep.domain.itinerary

import me.chnu.treep.annotation.ReadService
import me.chnu.treep.domain.itinerary.entity.Itinerary
import me.chnu.treep.domain.itinerary.entity.ItineraryRepository

@ReadService
internal class ItineraryReadService(
    private val itineraryRepository: ItineraryRepository
) {
    fun getAll(planId: Long): List<Itinerary> =
        itineraryRepository.findAllByTripPlanId(planId)
}