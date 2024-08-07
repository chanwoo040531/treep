package me.chnu.treep.presentation.api

import me.chnu.treep.config.AuthUser
import me.chnu.treep.domain.plan.PlanReadService
import me.chnu.treep.domain.plan.PlanWriteService
import me.chnu.treep.presentation.ApiResponse
import me.chnu.treep.util.toURI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/plans")
internal class PlanController(
    private val planWriteService: PlanWriteService,
    private val planReadService: PlanReadService,
) {

    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: PlanRequest,
    ): ResponseEntity<ApiResponse<String>> {
        request.toPlan(userId = authUser.userId).let(planWriteService::create)

        return ResponseEntity.created("/api/v1/plans/${authUser.userId}".toURI())
            .body(ApiResponse.success("여행 계획이 생성되었습니다"))
    }

    @GetMapping
    fun getAll(): ResponseEntity<ApiResponse<List<PlanResponse>>> {
        val response = planReadService.getAll().map(PlanResponse::from)
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @GetMapping("/{plan-id}")
    fun get(@PathVariable(value = "plan-id") planId: Long): ResponseEntity<ApiResponse<PlanResponse>> {
        val response = PlanResponse.from(planReadService.get(planId))
        return ResponseEntity.ok(ApiResponse.success(response))
    }
}