package me.chnu.treep.presentation.api

import me.chnu.treep.config.AuthUser
import me.chnu.treep.domain.Key
import me.chnu.treep.domain.itinerary.ItineraryReadService
import me.chnu.treep.domain.itinerary.ItineraryRepository
import me.chnu.treep.domain.itinerary.ItineraryWriteService
import me.chnu.treep.presentation.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/plans/{plan-id}/itineraries")
internal class ItineraryController(
    private val itineraryReadService: ItineraryReadService,
    private val itineraryWriteService: ItineraryWriteService,
) {
    @PostMapping("/dining")
    fun createDiningItinerary(
        authUser: AuthUser,
        @PathVariable(value = "plan-id") planId: Key,
        @RequestBody request: DiningRequest,
    ): ResponseEntity<Unit> {
        itineraryWriteService.createItinerary(itineraryData = request.toItineraryData(planId))
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/stay")
    fun createStayItinerary(
        authUser: AuthUser,
        @PathVariable(value = "plan-id") planId: Key,
        @RequestBody request: StayRequest,
    ): ResponseEntity<Unit> {
        itineraryWriteService.createItinerary(itineraryData = request.toItineraryData(planId))
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun getAll(
        authUser: AuthUser,
        @PathVariable(value = "plan-id") planId: Key,
    ): ResponseEntity<ApiResponse<List<ItineraryResponse>>> {
        val response = itineraryReadService.getAll(planId)
            .map(ItineraryResponse::from)

        return ResponseEntity.ok(ApiResponse.success(response))
    }
}