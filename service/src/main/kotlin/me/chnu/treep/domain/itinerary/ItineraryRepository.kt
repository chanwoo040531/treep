package me.chnu.treep.domain.itinerary

import me.chnu.treep.domain.Key
import me.chnu.treep.domain.itinerary.entity.Itinerary
import me.chnu.treep.domain.itinerary.entity.ItineraryType
import org.springframework.data.jpa.repository.JpaRepository

internal interface ItineraryRepository : JpaRepository<Itinerary, Key> {
    fun findAllByTripPlanIdAndItineraryType(tripPlanId: Key, type: ItineraryType): List<Itinerary>

    fun findAllByTripPlanId(planId: Key): List<Itinerary>
}
