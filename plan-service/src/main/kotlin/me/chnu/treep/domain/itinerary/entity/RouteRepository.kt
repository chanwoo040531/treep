package me.chnu.treep.domain.itinerary.entity

import org.springframework.data.mongodb.repository.MongoRepository

interface RouteRepository : MongoRepository<Route, String>