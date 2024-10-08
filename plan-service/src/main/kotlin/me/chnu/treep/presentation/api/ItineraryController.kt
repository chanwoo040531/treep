package me.chnu.treep.presentation.api

import me.chnu.treep.annotation.LoginRequired
import me.chnu.treep.application.PlanItineraryUseCase
import me.chnu.treep.config.web.AuthUser
import me.chnu.treep.domain.itinerary.ItineraryReadService
import me.chnu.treep.domain.itinerary.ItineraryWriteService
import me.chnu.treep.presentation.ApiResponse
import me.chnu.treep.util.toURI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/plans/{plan-id}/itineraries")
internal class ItineraryController(
    private val planItineraryUseCase: PlanItineraryUseCase,
    private val itineraryReadService: ItineraryReadService,
    private val itineraryWriteService: ItineraryWriteService,
) {
    @LoginRequired
    @PostMapping
    fun create(
        authUser: AuthUser,
        @PathVariable(value = "plan-id") planId: Long,
        @RequestBody request: ItineraryRequest,
    ): ResponseEntity<ApiResponse<String>> {
        planItineraryUseCase.saveItinerary(planId, request)

        return ResponseEntity.created("/v1/plans/${authUser.userId}".toURI())
            .body(ApiResponse.success("일정이 생성되었습니다"))
    }

    @GetMapping
    fun getAll(
        authUser: AuthUser,
        @PathVariable(value = "plan-id") planId: Long,
    ): ResponseEntity<ApiResponse<List<ItineraryResponse>>> =
        itineraryReadService.getAll(planId)
            .map(ItineraryResponse::from)
            .let { ApiResponse.success(it) }
            .let { ResponseEntity.ok(it) }

    @GetMapping("/{itinerary-id}")
    fun get(
        @PathVariable(value = "plan-id") planId: Long,
        @PathVariable(value = "itinerary-id") itineraryId: Long,
    ): ResponseEntity<ApiResponse<ItineraryResponse>> =
        itineraryReadService.get(itineraryId)
            .let(ItineraryResponse::from)
            .let { ApiResponse.success(it) }
            .let { ResponseEntity.ok(it) }

    @LoginRequired
    @PutMapping("/{itinerary-id}")
    fun update(
        @PathVariable(value = "plan-id") planId: Long,
        @PathVariable(value = "itinerary-id") itineraryId: Long,
        @RequestBody request: ItineraryRequest,
    ): ResponseEntity<ApiResponse<String>> {
        itineraryWriteService.update(itineraryId, request)

        return ResponseEntity.ok(ApiResponse.success("일정이 수정되었습니다"))
    }
}