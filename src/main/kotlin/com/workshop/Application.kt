package com.workshop

import com.workshop.routes.analyticsRoutes
import com.workshop.routes.orderRoutes
import com.workshop.routes.restaurantRoutes
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        watchPaths = listOf("classes", "resources"),
        module = Application::configureApp
    ).start(wait = true)
}

fun Application.configureApp() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to (cause.message ?: "Internal server error"))
            )
        }
    }

    routing {
        // Serve static files (dashboard UI)
        staticResources("/", "static") {
            default("index.html")
        }

        // API routes
        route("/api") {
            restaurantRoutes()
            orderRoutes()
            analyticsRoutes()
        }

        // Health check
        get("/health") {
            call.respond(mapOf("status" to "ok", "message" to "Food Delivery API is running!"))
        }
    }
}
