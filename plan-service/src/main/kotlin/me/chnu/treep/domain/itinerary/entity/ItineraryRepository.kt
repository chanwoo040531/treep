package me.chnu.treep.domain.itinerary.entity

import org.springframework.data.jpa.repository.JpaRepository

internal interface ItineraryRepository : JpaRepository<Itinerary, Long>