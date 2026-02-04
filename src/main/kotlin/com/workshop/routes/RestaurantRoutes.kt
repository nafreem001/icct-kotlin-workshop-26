package com.workshop.routes

import com.workshop.services.MenuService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class OpenStatusResponse(val restaurantId: String, val time: String, val isOpen: Boolean)

fun Route.restaurantRoutes() {
    route("/restaurants") {

        // GET /restaurants - List all restaurants (without full menus)
        get {
            val restaurants = MenuService.getRestaurants().map { it.copy(menu = emptyList()) }
            call.respond(restaurants)
        }

        // GET /restaurants/{id} - Get a specific restaurant with its menu
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing restaurant ID")
            )
            val restaurant = MenuService.getRestaurantById(id) ?: return@get call.respond(
                HttpStatusCode.NotFound, mapOf("error" to "Restaurant not found: $id")
            )
            call.respond(restaurant)
        }

        // GET /restaurants/{id}/menu?maxPrice=200&dietary=vegetarian
        // Level 1A: This endpoint has bugs in the filter logic!
        get("/{id}/menu") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing restaurant ID")
            )
            val maxPrice = call.request.queryParameters["maxPrice"]?.toDoubleOrNull()
            val dietary = call.request.queryParameters["dietary"]

            try {
                val items = MenuService.filterMenu(id, maxPrice, dietary) ?: return@get call.respond(
                    HttpStatusCode.NotFound, mapOf("error" to "Restaurant not found: $id")
                )
                call.respond(items)
            } catch (e: NullPointerException) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Server error: ${e.message}. Hint: Check null safety in MenuService.filterMenu()")
                )
            }
        }

        // GET /restaurants/{id}/open?time=14:30
        // Level 1C: This endpoint has bugs in the hours logic!
        get("/{id}/open") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing restaurant ID")
            )
            val time = call.request.queryParameters["time"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing 'time' query parameter (format: HH:mm)")
            )

            val result = MenuService.isOpenAt(id, time)
            result.fold(
                onSuccess = { isOpen ->
                    call.respond(OpenStatusResponse(restaurantId = id, time = time, isOpen = isOpen))
                },
                onFailure = { error ->
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to (error.message ?: "Unknown error"))
                    )
                }
            )
        }

        // GET /restaurants/search?cuisine=Filipino&sortBy=rating&minRating=4.0
        // Level 3A: Students implement SearchService.searchRestaurants()
        get("/search") {
            val cuisine = call.request.queryParameters["cuisine"]
            val minRating = call.request.queryParameters["minRating"]?.toDoubleOrNull()
            val maxDeliveryTime = call.request.queryParameters["maxDeliveryTime"]?.toIntOrNull()
            val sortBy = call.request.queryParameters["sortBy"]

            val results = com.workshop.services.SearchService.searchRestaurants(
                cuisine = cuisine,
                minRating = minRating,
                maxDeliveryTime = maxDeliveryTime,
                sortBy = sortBy
            )
            call.respond(results.map { it.copy(menu = emptyList()) })
        }
    }
}
