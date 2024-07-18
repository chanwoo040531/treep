package me.chnu.treep.presentation.api

import me.chnu.treep.config.AuthUser
import me.chnu.treep.domain.itinerary.ItineraryReadService
import me.chnu.treep.domain.itinerary.ItineraryWriteService
import me.chnu.treep.presentation.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/plans/{plan-id}/itineraries")
internal class ItineraryController(
    private val itineraryReadService: ItineraryReadService,
    private val itineraryWriteService: ItineraryWriteService,
) {
    @GetMapping
    fun getAll(
        authUser: AuthUser,
        @PathVariable(value = "plan-id") planId: Long,
    ): ResponseEntity<ApiResponse<List<ItineraryResponse>>> {
        val response = itineraryReadService.getAll(planId)
            .map(ItineraryResponse::from)

        return ResponseEntity.ok(ApiResponse.success(response))
    }
}