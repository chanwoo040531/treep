package me.chnu.treep.presentation.api

import me.chnu.treep.annotation.LoginRequired
import me.chnu.treep.config.web.AuthUser
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
        val plan = request.toPlan(userId = authUser.userId)

        return planWriteService.create(plan)
            .let { "/api/v1/plans/${it.id}".toURI() }
            .let { ResponseEntity.created(it)
                    .body(ApiResponse.success("여행 계획이 생성되었습니다")) }
    }

    @LoginRequired
    @GetMapping
    fun getAll(): ResponseEntity<ApiResponse<List<PlanResponse>>> =
        planReadService.getAll()
            .map(PlanResponse::from)
            .let { ApiResponse.success(it) }
            .let { ResponseEntity.ok(it) }

    @LoginRequired
    @GetMapping("/{plan-id}")
    fun get(@PathVariable(value = "plan-id") planId: Long): ResponseEntity<ApiResponse<PlanResponse>> =
        planReadService.get(planId)
            .let(PlanResponse::from)
            .let { ApiResponse.success(it) }
            .let { ResponseEntity.ok(it) }

    @LoginRequired
    @PutMapping("/{plan-id}")
    fun update(
        @PathVariable(value = "plan-id") planId: Long,
        @RequestBody request: PlanRequest,
    ): ResponseEntity<ApiResponse<String>> {
        planWriteService.update(planId, request)

        return ResponseEntity.ok(ApiResponse.success("여행 계획이 수정되었습니다"))
    }

    @LoginRequired
    @DeleteMapping("/{plan-id}")
    fun delete(@PathVariable(value = "plan-id") planId: Long): ResponseEntity<ApiResponse<String>> {
        planWriteService.delete(planId)

        return ResponseEntity.ok(ApiResponse.success("여행 계획이 삭제되었습니다"))
    }
}