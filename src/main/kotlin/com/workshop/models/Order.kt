package com.workshop.models

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String,
    val restaurantId: String,
    val customerId: String,
    val items: List<LineItem>,
    val status: OrderStatus = OrderStatus.Placed,
    val specialInstructions: String? = null,
    val createdAt: String = "" // ISO timestamp string for simplicity
)

@Serializable
data class LineItem(
    val menuItemId: String,
    val menuItemName: String,
    val quantity: Int,
    val unitPrice: Double
)
