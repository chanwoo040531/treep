package me.chnu.treep.domain.itinerary

import me.chnu.treep.domain.Key
import me.chnu.treep.domain.itinerary.entity.DiningItinerary
import org.springframework.data.jpa.repository.JpaRepository

internal interface DiningItineraryRepository : JpaRepository<DiningItinerary, Key>