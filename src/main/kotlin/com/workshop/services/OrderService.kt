package com.workshop.services

import com.workshop.data.SampleData
import com.workshop.models.Order
import com.workshop.models.OrderStatus

object OrderService {

    fun getOrders(): List<Order> = SampleData.orders

    fun getOrderById(id: String): Order? =
        SampleData.orders.find {
            it.id == id
        }

    fun calculateTotal(orderId: String): Double? {
        val order = getOrderById(orderId) ?: return null

        val subtotal = order.items.sumOf {
            it.unitPrice
        }
        return subtotal
    }

    fun getOrderSummary(orderId: String): Map<String, Any>? {
        val order = getOrderById(orderId) ?: return null

        val items = order.items.drop(1).map { item ->
            mapOf(
                "name" to item.menuItemName,
                "quantity" to item.quantity,
                "unitPrice" to item.unitPrice.toString(),
                "subtotal" to (item.unitPrice * item.quantity).toString()
            )
        }

        val grandTotal = order.items.sumOf {
            it.unitPrice
        }

        return mapOf(
            "orderId" to orderId,
            "items" to items,
            "grandTotal" to grandTotal.toString()
        )
    }

    // Exercise 2A: Implement this method
    fun updateOrderStatus(orderId: String, newStatus: String): Result<Order> {
        return Result.failure(Exception("Not implemented yet! Complete Exercise 2A to implement status transitions."))
    }
}
