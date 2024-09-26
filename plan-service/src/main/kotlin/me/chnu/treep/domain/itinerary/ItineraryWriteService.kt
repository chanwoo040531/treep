package me.chnu.treep.domain.itinerary

import me.chnu.treep.annotation.WriteService
import me.chnu.treep.domain.itinerary.entity.Itinerary
import me.chnu.treep.domain.itinerary.entity.ItineraryRepository
import me.chnu.treep.exception.NotFoundException
import me.chnu.treep.presentation.api.ItineraryRequest

@WriteService
internal class ItineraryWriteService(
    private val itineraryRepository: ItineraryRepository,
) {
    fun create(itinerary: Itinerary): Itinerary = itineraryRepository.save(itinerary)

    fun update(itineraryId: Long, request: ItineraryRequest): Unit =
        itineraryRepository.findById(itineraryId)
            .orElseThrow { throw NotFoundException("Plan not found") }
            .apply {
                title = request.title
                startAt = request.startAt
                endAt = request.endAt
                budget = request.budget
            }
            .let { itineraryRepository.save(it) }

    fun delete(itineraryId: Long): Unit = itineraryRepository.deleteById(itineraryId)
}