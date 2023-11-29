package me.chnu.treep.domain.itinerary

import me.chnu.treep.annotation.WriteService
import me.chnu.treep.domain.itinerary.entity.Itinerary

@WriteService
internal class ItineraryWriteService(
    private val itineraryRepository: ItineraryRepository
) {
    fun createItinerary(itineraryData: ItineraryData): Itinerary =
        itineraryRepository.save(itineraryData.toItinerary())
}
