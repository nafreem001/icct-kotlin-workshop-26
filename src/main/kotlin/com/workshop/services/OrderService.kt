package com.workshop.services

import com.workshop.data.SampleData
import com.workshop.models.Order
import com.workshop.models.OrderStatus

object OrderService {

    fun getOrders(): List<Order> = SampleData.orders

    fun getOrderById(id: String): Order? =
        SampleData.orders.find { it.id == id }

    /**
     * Calculates the total price of an order.
     *
     * Business rules:
     * - Total = sum of (unitPrice × quantity) for each line item
     * - If the subtotal is over ₱500, apply a 10% discount
     * - Return the final total
     *
     * @return The order total, or null if order not found
     */
    fun calculateTotal(orderId: String): Double? {
        val order = getOrderById(orderId) ?: return null

        val subtotal = order.items.sumOf { it.unitPrice }
        return subtotal
    }

    // Exercise 2A: Implement this method
    fun updateOrderStatus(orderId: String, newStatus: String): Result<Order> {
        return Result.failure(Exception("Not implemented yet! Complete Exercise 2A to implement status transitions."))
    }
}
