package com.workshop.routes

import com.workshop.services.AnalyticsService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.analyticsRoutes() {
    route("/analytics") {

        // GET /analytics/summary
        // Level 3B: Students implement the analytics aggregation
        get("/summary") {
            val summary = AnalyticsService.getAnalyticsSummary()
            call.respond(summary)
        }
    }
}
