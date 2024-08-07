package me.chnu.treep.presentation.api

import me.chnu.treep.config.AuthUser
import me.chnu.treep.domain.itinerary.ItineraryReadService
import me.chnu.treep.domain.itinerary.ItineraryWriteService
import me.chnu.treep.presentation.ApiResponse
import me.chnu.treep.util.toURI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/plans/{plan-id}/itineraries")
internal class ItineraryController(
    private val itineraryReadService: ItineraryReadService,
    private val itineraryWriteService: ItineraryWriteService,
) {
    @PostMapping
    fun create(
        authUser: AuthUser,
        @PathVariable(value = "plan-id") planId: Long,
        @RequestBody request: ItineraryRequest,
    ): ResponseEntity<ApiResponse<String>> {
        request.toItinerary().let(itineraryWriteService::create)

        return ResponseEntity.created("/v1/plans/${authUser.userId}".toURI())
            .body(ApiResponse.success("일정이 생성되었습니다"))
    }

    @GetMapping
    fun getAll(
        authUser: AuthUser,
        @PathVariable(value = "plan-id") planId: Long,
    ): ResponseEntity<ApiResponse<List<ItineraryResponse>>> {
        val response = itineraryReadService.getAll(planId)
            .map(ItineraryResponse::from)

        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @GetMapping("/{itinerary-id}")
    fun get(
        @PathVariable(value = "plan-id") planId: Long,
        @PathVariable(value = "itinerary-id") itineraryId: Long,
    ): ResponseEntity<ApiResponse<ItineraryResponse>> {
        val response = itineraryReadService.get(itineraryId)
            .let(ItineraryResponse::from)

        return ResponseEntity.ok(ApiResponse.success(response))
    }
}