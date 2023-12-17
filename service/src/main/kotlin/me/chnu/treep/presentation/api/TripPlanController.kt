package me.chnu.treep.presentation.api

import me.chnu.treep.config.AuthUser
import me.chnu.treep.domain.Key
import me.chnu.treep.domain.plan.TripPlan
import me.chnu.treep.domain.plan.TripPlanReadService
import me.chnu.treep.domain.plan.TripPlanWriteService
import me.chnu.treep.presentation.ApiResponse
import me.chnu.treep.util.toURI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/plans")
internal class TripPlanController(
    private val tripPlanWriteService: TripPlanWriteService,
    private val tripPlanReadService: TripPlanReadService,
) {

    /**
     * 이 부분은 제가 예전에 말했었던 부분이네요
     * 프론트 개발자를 구했으니 성공 후 redirect 에 대한 부분은 프론트에게 맏기는게 어떨까요?
     */
    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: PlanRequest,
    ): ResponseEntity<ApiResponse<TripPlan>> {
        val response = tripPlanWriteService.create(request.toPlanData(userId = authUser.userId))
        return ResponseEntity.created("/api/v1/plans/${authUser.userId}".toURI())
            .body(ApiResponse.success(response))
    }

    // ResponseEntity 를 지양해볼 생각은... 아직 없으신가요...? 아래는 depth 가 너무 깊네요 ㅋㅋㅋㅋ
    @GetMapping
    fun getAll(
        authUser: AuthUser,
    ): ResponseEntity<ApiResponse<List<PlanResponse>>> {
        val response = tripPlanReadService.getAll().map(PlanResponse::from)
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @GetMapping("/{plan-id}")
    fun get(
        authUser: AuthUser,
        @PathVariable(value = "plan-id") planId: Key,
    ): ResponseEntity<ApiResponse<PlanResponse>> {
        val response = tripPlanReadService.get(planId).let(PlanResponse::from)
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @PutMapping("/{plan-id}")
    fun update(
        authUser: AuthUser,
        @PathVariable(value = "plan-id") planId: Key,
        @RequestBody request: PlanRequest,
    ): ResponseEntity<Unit> {
        tripPlanWriteService.update(request.toPlanData(userId = authUser.userId))
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{plan-id}")
    fun delete(
        authUser: AuthUser,
        @PathVariable(value = "plan-id") planId: Key,
    ): ResponseEntity<Unit> {
        tripPlanWriteService.delete(planId)

        return ResponseEntity.noContent().build()
    }
}