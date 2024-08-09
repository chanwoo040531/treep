package me.chnu.treep.application

import me.chnu.treep.annotation.UseCase
import me.chnu.treep.domain.itinerary.ItineraryReadService
import me.chnu.treep.domain.itinerary.ItineraryWriteService
import me.chnu.treep.domain.plan.PlanReadService
import me.chnu.treep.presentation.api.ItineraryRequest

@UseCase
internal class PlanItineraryUseCase(
    private val planReadService: PlanReadService,
    private val itineraryWriteService: ItineraryWriteService,
    private val itineraryReadService: ItineraryReadService,
) {

    fun saveItinerary(planId: Long, itineraryRequest: ItineraryRequest) {
        val plan = planReadService.get(planId)
        val parent = itineraryRequest.parentId
            ?.let { itineraryReadService.get(it) }

        val itinerary = itineraryRequest.toItinerary(plan, parent)
        itineraryWriteService.create(itinerary)
    }
}