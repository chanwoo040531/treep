package me.chnu.treep.presentation.api

import me.chnu.treep.config.AuthUser
import me.chnu.treep.domain.plan.TripPlanReadService
import me.chnu.treep.domain.plan.TripPlanWriteService
import me.chnu.treep.domain.plan.entity.TripPlan
import me.chnu.treep.presentation.ApiResponse
import me.chnu.treep.util.toURI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/plans")
internal class TripPlanController(
    private val tripPlanWriteService: TripPlanWriteService,
    private val tripPlanReadService: TripPlanReadService,
) {

    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: PlanRequest,
    ): ResponseEntity<ApiResponse<String>> {
        request.toPlan(userId = authUser.userId).let(tripPlanWriteService::create)

        return ResponseEntity.created("/api/v1/plans/${authUser.userId}".toURI())
            .body(ApiResponse.success("여행 계획이 생성되었습니다"))
    }

    @GetMapping
    fun getAll(
        authUser: AuthUser,
    ): ResponseEntity<ApiResponse<List<PlanResponse>>> {
        val response = tripPlanReadService.getAll().map(PlanResponse::from)
        return ResponseEntity.ok(ApiResponse.success(response))
    }
}