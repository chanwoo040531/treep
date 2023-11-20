package me.chnu.treep.presentation.api

import me.chnu.treep.config.AuthUser
import me.chnu.treep.domain.Key
import me.chnu.treep.domain.plan.TripPlan
import me.chnu.treep.domain.plan.TripPlanReadService
import me.chnu.treep.domain.plan.TripPlanWriteService
import me.chnu.treep.presentation.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/plans")
internal class TripPlanController(
    private val tripPlanWriteService: TripPlanWriteService,
    private val tripPlanReadService: TripPlanReadService,
) {

    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: PlanRequest,
    ): ResponseEntity<ApiResponse<TripPlan>> {
        val response = tripPlanWriteService.create(request.toPlanInfo(userId = authUser.userId))
        return ResponseEntity.created(URI.create("/api/v1/plans/${authUser.userId}"))
            .body(ApiResponse.success(response))
    }

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
        tripPlanWriteService.update(request.toPlanInfo(userId = authUser.userId))
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