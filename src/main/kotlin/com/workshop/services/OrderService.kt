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

        // =====================================================
        // LEVEL 1B BUG #1: Doesn't multiply by quantity!
        // Each line item has a unitPrice AND a quantity.
        // This code only sums the unit prices, ignoring quantity.
        //
        // FIX: Multiply it.unitPrice * it.quantity
        // =====================================================
        val subtotal = order.items.sumOf { it.unitPrice }

        // =====================================================
        // LEVEL 1B BUG #2: Discount is not applied!
        // Orders over ₱500 should get a 10% discount.
        // This code just returns the subtotal without checking.
        //
        // HINT: Use an if expression:
        //   if (subtotal > 500) subtotal * 0.9 else subtotal
        // =====================================================
        return subtotal
    }

    // =====================================================
    // LEVEL 2A EXERCISE: Implement updateOrderStatus()
    // =====================================================
    // TODO: Implement this method to update an order's status.
    //
    // Steps:
    // 1. Find the order by ID (return error message if not found)
    // 2. Parse the newStatus string into an OrderStatus sealed class instance
    //    HINT: Use a 'when' expression on the string:
    //      when (newStatus) {
    //          "Placed" -> OrderStatus.Placed
    //          "Preparing" -> OrderStatus.Preparing
    //          "OutForDelivery" -> OrderStatus.OutForDelivery
    //          "Delivered" -> OrderStatus.Delivered
    //          "Cancelled" -> OrderStatus.Cancelled
    //          else -> return Result.failure(...)
    //      }
    // 3. Check if the transition is valid using canTransitionTo()
    //    (You need to implement canTransitionTo in OrderStatus.kt first!)
    // 4. If valid, update the order in the list and return success
    // 5. If invalid, return an error message explaining why
    //
    // To update the order in the list:
    //   val index = SampleData.orders.indexOfFirst { it.id == orderId }
    //   SampleData.orders[index] = order.copy(status = parsedStatus)
    //
    // Return type is Result<Order> - use Result.success(order) or Result.failure(Exception("message"))
    fun updateOrderStatus(orderId: String, newStatus: String): Result<Order> {
        // TODO: Replace this with your implementation
        return Result.failure(Exception("Not implemented yet! Complete Exercise 2A to implement status transitions."))
    }
}
