package me.chnu.treep.domain.itinerary

import me.chnu.treep.annotation.ReadService
import me.chnu.treep.domain.itinerary.entity.Itinerary
import me.chnu.treep.domain.itinerary.entity.ItineraryRepository
import me.chnu.treep.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull

@ReadService
internal class ItineraryReadService(
    private val itineraryRepository: ItineraryRepository,
) {
    fun getAll(): List<Itinerary> =
        itineraryRepository.findAll()

    fun get(itineraryId: Long): Itinerary =
        itineraryRepository.findByIdOrNull(itineraryId)
            ?: throw NotFoundException("여행 계획을 찾을 수 없습니다")
}
