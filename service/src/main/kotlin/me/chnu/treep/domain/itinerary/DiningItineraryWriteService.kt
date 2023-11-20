package me.chnu.treep.domain.itinerary

import me.chnu.treep.annotation.WriteService

@WriteService
internal class DiningItineraryWriteService(
    private val diningItineraryRepository: DiningItineraryRepository,
) {
    fun create(diningInfo: DiningInfo) =
        diningItineraryRepository.save(diningInfo.toDiningItinerary())
}