package me.chnu.treep.domain.itinerary

import me.chnu.treep.annotation.WriteService
import me.chnu.treep.domain.itinerary.entity.Itinerary
import me.chnu.treep.domain.itinerary.entity.ItineraryRepository

@WriteService
internal class ItineraryWriteService(
    private val itineraryRepository: ItineraryRepository
) {
    fun create(itinerary: Itinerary): Itinerary =
        itineraryRepository.save(itinerary)
}
