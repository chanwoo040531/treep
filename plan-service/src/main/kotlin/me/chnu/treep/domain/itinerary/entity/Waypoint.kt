package me.chnu.treep.domain.itinerary.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document(collation = "waypoints")
class Waypoint(
    @Id
    val id: String,

    var type: WaypointType,

    var cost: Double,
    var point: GeoJsonPoint,

    var startTime: ZonedDateTime,
    var endTime: ZonedDateTime,

    @Indexed
    val itineraryId: Long,
)