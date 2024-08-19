package me.chnu.treep.domain.itinerary

import me.chnu.treep.annotation.WriteService
import me.chnu.treep.domain.itinerary.entity.Itinerary
import me.chnu.treep.domain.itinerary.entity.ItineraryRepository
import me.chnu.treep.exception.NotFoundException
import me.chnu.treep.presentation.api.ItineraryRequest

@WriteService
internal class ItineraryWriteService(
    private val itineraryRepository: ItineraryRepository
) {
    fun create(itinerary: Itinerary): Itinerary =
        itineraryRepository.save(itinerary)

    fun update(itineraryId: Long, request: ItineraryRequest): Unit =
        itineraryRepository.findById(itineraryId)
            .orElseThrow { throw NotFoundException("Itinerary not found") }
            .apply {
                title = request.title
                startAt = request.startAt
                endAt = request.endAt
                cost = request.cost
                description = request.description
            }
            .let { itineraryRepository.save(it) }
}
