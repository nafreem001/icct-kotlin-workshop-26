package com.workshop.routes

import com.workshop.services.LoyaltyService
import com.workshop.services.RecommendationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRoutes() {
    route("/customers") {

        // GET /customers/{id}/loyalty
        // Level 2C: Students implement LoyaltyService.calculateTier() and getLoyaltyInfo()
        get("/{id}/loyalty") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing customer ID")
            )
            val info = LoyaltyService.getLoyaltyInfo(id) ?: return@get call.respond(
                HttpStatusCode.NotFound, mapOf("error" to "Customer not found: $id")
            )
            call.respond(info)
        }

        // GET /customers/{id}/recommendations
        // Level 3C: Students implement RecommendationService.getRecommendations()
        get("/{id}/recommendations") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing customer ID")
            )
            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 5
            val recommendations = RecommendationService.getRecommendations(id, limit)
            if (recommendations.isEmpty()) {
                call.respond(
                    HttpStatusCode.NotFound,
                    mapOf("error" to "No recommendations available for customer: $id")
                )
            } else {
                call.respond(recommendations)
            }
        }
    }
}
