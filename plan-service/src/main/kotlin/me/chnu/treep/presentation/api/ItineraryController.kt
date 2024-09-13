package me.chnu.treep.presentation.api

import me.chnu.treep.annotation.LoginRequired
import me.chnu.treep.config.web.AuthUser
import me.chnu.treep.domain.itinerary.ItineraryReadService
import me.chnu.treep.domain.itinerary.ItineraryWriteService
import me.chnu.treep.domain.itinerary.entity.ItineraryId
import me.chnu.treep.presentation.ApiResponse
import me.chnu.treep.util.toURI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/itineraries")
internal class ItineraryController(
    private val itineraryWriteService: ItineraryWriteService,
    private val itineraryReadService: ItineraryReadService,
) {

    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: ItineraryRequest,
    ): ResponseEntity<ApiResponse<String>> {
        val itinerary = request.toItinerary(userId = authUser.userId)

        return itineraryWriteService.create(itinerary)
            .let { "/api/v1/itineraries/${it.id}".toURI() }
            .let { ResponseEntity.created(it)
                    .body(ApiResponse.success("여행 계획이 생성되었습니다")) }
    }

    @GetMapping
    fun getAll(): ResponseEntity<ApiResponse<List<ItineraryResponse>>> =
        itineraryReadService.getAll()
            .map(ItineraryResponse::from)
            .let { ApiResponse.success(it) }
            .let { ResponseEntity.ok(it) }

    @GetMapping("/{itinerary-id}")
    fun get(@PathVariable(value = "itinerary-id") itineraryId: ItineraryId): ResponseEntity<ApiResponse<ItineraryResponse>> =
        itineraryReadService.get(itineraryId)
            .let(ItineraryResponse::from)
            .let { ApiResponse.success(it) }
            .let { ResponseEntity.ok(it) }

    @LoginRequired
    @PutMapping("/{itinerary-id}")
    fun update(
        @PathVariable(value = "itinerary-id") itineraryId: ItineraryId,
        @RequestBody request: ItineraryRequest,
    ): ResponseEntity<ApiResponse<String>> {
        itineraryWriteService.update(itineraryId, request)

        return ResponseEntity.ok(ApiResponse.success("여행 계획이 수정되었습니다"))
    }

    @LoginRequired
    @DeleteMapping("/{itinerary-id}")
    fun delete(@PathVariable(value = "itinerary-id") itineraryId: ItineraryId): ResponseEntity<ApiResponse<String>> {
        itineraryWriteService.delete(itineraryId)

        return ResponseEntity.ok(ApiResponse.success("여행 계획이 삭제되었습니다"))
    }
}