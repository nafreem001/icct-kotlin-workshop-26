package com.workshop.routes

import com.workshop.services.DeliveryService
import com.workshop.services.OrderService
import com.workshop.services.PrepService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
data class StatusUpdateRequest(val newStatus: String)

fun Route.orderRoutes() {
    route("/orders") {

        // GET /orders - List all orders
        get {
            call.respond(OrderService.getOrders())
        }

        // GET /orders/{id} - Get a specific order
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing order ID")
            )
            val order = OrderService.getOrderById(id) ?: return@get call.respond(
                HttpStatusCode.NotFound, mapOf("error" to "Order not found: $id")
            )
            call.respond(order)
        }

        // GET /orders/{id}/summary
        // Level 1D: The summary has bugs!
        get("/{id}/summary") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing order ID")
            )
            val summary = OrderService.getOrderSummary(id) ?: return@get call.respond(
                HttpStatusCode.NotFound, mapOf("error" to "Order not found: $id")
            )
            val json = buildJsonObject {
                put("orderId", summary["orderId"] as String)
                put("grandTotal", summary["grandTotal"] as String)
                putJsonArray("items") {
                    @Suppress("UNCHECKED_CAST")
                    for (item in summary["items"] as List<Map<String, Any>>) {
                        addJsonObject {
                            put("name", item["name"] as String)
                            put("quantity", (item["quantity"] as Int))
                            put("unitPrice", item["unitPrice"] as String)
                            put("subtotal", item["subtotal"] as String)
                        }
                    }
                }
            }
            call.respond(json)
        }

        // GET /orders/{id}/total
        // Level 1B: The calculation has bugs!
        get("/{id}/total") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing order ID")
            )
            val total = OrderService.calculateTotal(id) ?: return@get call.respond(
                HttpStatusCode.NotFound, mapOf("error" to "Order not found: $id")
            )
            val order = OrderService.getOrderById(id)!!
            call.respond(mapOf(
                "orderId" to id,
                "items" to order.items.size.toString(),
                "total" to "%.2f".format(total)
            ))
        }

        // PUT /orders/{id}/status
        // Level 2A: Students implement status transition logic
        put("/{id}/status") {
            val id = call.parameters["id"] ?: return@put call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing order ID")
            )

            val request = try {
                call.receive<StatusUpdateRequest>()
            } catch (e: Exception) {
                return@put call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Invalid request body. Expected: {\"newStatus\": \"Preparing\"}")
                )
            }

            val result = OrderService.updateOrderStatus(id, request.newStatus)
            result.fold(
                onSuccess = { order ->
                    call.respond(order)
                },
                onFailure = { error ->
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Unknown error"))
                    )
                }
            )
        }

        // POST /orders/{id}/prepare
        // Level 2D: Students implement PrepService with coroutines
        post("/{id}/prepare") {
            val id = call.parameters["id"] ?: return@post call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing order ID")
            )
            val result = PrepService.startPreparation(id, CoroutineScope(Dispatchers.Default))
            result.fold(
                onSuccess = { progress -> call.respond(progress) },
                onFailure = { error ->
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Unknown error"))
                    )
                }
            )
        }

        // GET /orders/{id}/prep-status
        // Level 2D: Check preparation progress
        get("/{id}/prep-status") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing order ID")
            )
            val progress = PrepService.getPrepStatus(id) ?: return@get call.respond(
                HttpStatusCode.NotFound, mapOf("error" to "No preparation started for order: $id")
            )
            call.respond(progress)
        }

        // GET /orders/{id}/delivery-fee
        // Level 2B: Students implement delivery fee calculation
        get("/{id}/delivery-fee") {
            val id = call.parameters["id"] ?: return@get call.respond(
                HttpStatusCode.BadRequest, mapOf("error" to "Missing order ID")
            )

            val result = DeliveryService.calculateDeliveryFee(id)
            result.fold(
                onSuccess = { feeResult ->
                    call.respond(feeResult)
                },
                onFailure = { error ->
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to (error.message ?: "Unknown error"))
                    )
                }
            )
        }
    }
}
