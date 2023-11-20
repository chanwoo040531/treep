package me.chnu.treep.presentation.api

import me.chnu.treep.config.AuthUser
import me.chnu.treep.domain.Key
import me.chnu.treep.domain.itinerary.DiningItineraryWriteService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/plans/{plan-id}/itinerary")
internal class ItineraryController(
    private val itineraryWriteService: DiningItineraryWriteService
) {
    @PostMapping
    fun create(
        authUser: AuthUser,
        @PathVariable(value = "plan-id") planId: Key,
        @RequestBody request: DiningRequest,
    ) {
        itineraryWriteService.create(diningInfo = request.toDiningInfo(planId))
    }
}