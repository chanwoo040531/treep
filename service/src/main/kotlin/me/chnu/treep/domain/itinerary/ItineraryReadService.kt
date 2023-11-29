package me.chnu.treep.domain.itinerary

import me.chnu.treep.annotation.ReadService
import me.chnu.treep.domain.Key

@ReadService
internal class ItineraryReadService(
    private val itineraryRepository: ItineraryRepository
) {
    fun getAll(planId: Key): List<ItineraryInfo> =
        itineraryRepository.findAllByTripPlanId(planId).map(ItineraryInfo::from)
}