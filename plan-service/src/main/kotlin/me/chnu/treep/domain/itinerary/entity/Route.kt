package me.chnu.treep.domain.itinerary.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import kotlin.time.Duration

@JvmInline
value class RouteId(val value: String)

@Document(collation = "routes")
@CompoundIndex(def = "{'fromWaypoint': 1, 'toWaypoint': 1}")
class Route(
    @Id
    val id: RouteId,

    var type: RouteType,

    val distance: Double,
    var duration: Duration,
    var polyline: String,
    var cost: Double,

    val fromWaypointId: WaypointId,
    val toWaypointId: WaypointId,

    @Indexed
    val itineraryId: ItineraryId,
)