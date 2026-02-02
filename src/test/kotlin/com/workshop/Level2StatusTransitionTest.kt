package com.workshop

import com.workshop.data.SampleData
import com.workshop.models.OrderStatus
import com.workshop.services.OrderService
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Level 2A: Order Status Transition Tests
 *
 * Run with: ./gradlew test --tests "com.workshop.Level2StatusTransitionTest"
 *
 * Tests verify:
 *   1. OrderStatus.canTransitionTo() validates transitions correctly
 *   2. OrderService.updateOrderStatus() updates orders properly
 */
class Level2StatusTransitionTest {

    @BeforeEach
    fun resetOrders() {
        // Reset order statuses to original values so tests don't affect each other
        val statusMap = mapOf(
            "order-1" to OrderStatus.Placed,
            "order-2" to OrderStatus.Preparing,
            "order-3" to OrderStatus.OutForDelivery,
            "order-4" to OrderStatus.Delivered,
            "order-5" to OrderStatus.Placed,
            "order-6" to OrderStatus.Cancelled
        )
        SampleData.orders.forEachIndexed { index, order ->
            statusMap[order.id]?.let { originalStatus ->
                SampleData.orders[index] = order.copy(status = originalStatus)
            }
        }
    }

    // --- canTransitionTo() tests ---

    @Test
    fun `Placed can transition to Preparing`() {
        assertTrue(OrderStatus.Placed.canTransitionTo(OrderStatus.Preparing))
    }

    @Test
    fun `Placed can transition to Cancelled`() {
        assertTrue(OrderStatus.Placed.canTransitionTo(OrderStatus.Cancelled))
    }

    @Test
    fun `Placed cannot transition to Delivered`() {
        assertFalse(OrderStatus.Placed.canTransitionTo(OrderStatus.Delivered))
    }

    @Test
    fun `Placed cannot transition to OutForDelivery`() {
        assertFalse(OrderStatus.Placed.canTransitionTo(OrderStatus.OutForDelivery))
    }

    @Test
    fun `Preparing can transition to OutForDelivery`() {
        assertTrue(OrderStatus.Preparing.canTransitionTo(OrderStatus.OutForDelivery))
    }

    @Test
    fun `Preparing can transition to Cancelled`() {
        assertTrue(OrderStatus.Preparing.canTransitionTo(OrderStatus.Cancelled))
    }

    @Test
    fun `Preparing cannot transition to Placed`() {
        assertFalse(OrderStatus.Preparing.canTransitionTo(OrderStatus.Placed))
    }

    @Test
    fun `OutForDelivery can transition to Delivered`() {
        assertTrue(OrderStatus.OutForDelivery.canTransitionTo(OrderStatus.Delivered))
    }

    @Test
    fun `OutForDelivery can transition to Cancelled`() {
        assertTrue(OrderStatus.OutForDelivery.canTransitionTo(OrderStatus.Cancelled))
    }

    @Test
    fun `Delivered cannot transition to anything`() {
        assertFalse(OrderStatus.Delivered.canTransitionTo(OrderStatus.Placed))
        assertFalse(OrderStatus.Delivered.canTransitionTo(OrderStatus.Preparing))
        assertFalse(OrderStatus.Delivered.canTransitionTo(OrderStatus.OutForDelivery))
        assertFalse(OrderStatus.Delivered.canTransitionTo(OrderStatus.Cancelled))
    }

    @Test
    fun `Cancelled cannot transition to anything`() {
        assertFalse(OrderStatus.Cancelled.canTransitionTo(OrderStatus.Placed))
        assertFalse(OrderStatus.Cancelled.canTransitionTo(OrderStatus.Preparing))
        assertFalse(OrderStatus.Cancelled.canTransitionTo(OrderStatus.OutForDelivery))
        assertFalse(OrderStatus.Cancelled.canTransitionTo(OrderStatus.Delivered))
    }

    // --- updateOrderStatus() tests ---

    @Test
    fun `can advance order from Placed to Preparing`() {
        // Order 1 starts as Placed
        val result = OrderService.updateOrderStatus("order-1", "Preparing")
        assertTrue(result.isSuccess, "Should succeed: ${result.exceptionOrNull()?.message}")
        assertEquals(OrderStatus.Preparing, result.getOrNull()?.status)
    }

    @Test
    fun `cannot advance Delivered order`() {
        // Order 4 is Delivered
        val result = OrderService.updateOrderStatus("order-4", "Placed")
        assertTrue(result.isFailure, "Should fail: Delivered orders can't change status")
    }

    @Test
    fun `invalid status string returns error`() {
        val result = OrderService.updateOrderStatus("order-1", "Flying")
        assertTrue(result.isFailure, "Should fail for invalid status string")
    }

    @Test
    fun `nonexistent order returns error`() {
        val result = OrderService.updateOrderStatus("nonexistent", "Preparing")
        assertTrue(result.isFailure, "Should fail for nonexistent order")
    }
}
