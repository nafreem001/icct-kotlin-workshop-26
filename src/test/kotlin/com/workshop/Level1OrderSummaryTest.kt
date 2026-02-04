package com.workshop

import com.workshop.services.OrderService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Level 1D: Order Summary Tests
 *
 * Run with: ./gradlew test --tests "com.workshop.Level1OrderSummaryTest"
 *
 * These tests verify that OrderService.getOrderSummary() works correctly.
 * The summary should include ALL items with name, quantity, unit price, subtotal,
 * and a correctly formatted grand total.
 */
class Level1OrderSummaryTest {

    @Test
    fun `summary includes all items in the order`() {
        // Order 1 has 3 line items
        val summary = OrderService.getOrderSummary("order-1")
        assertNotNull(summary, "Should return summary for valid order")
        val items = summary["items"] as List<*>
        assertEquals(3, items.size, "Order 1 has 3 line items, but summary has ${items.size}")
    }

    @Test
    fun `summary includes first item`() {
        val summary = OrderService.getOrderSummary("order-1")
        assertNotNull(summary)
        val items = summary["items"] as List<Map<String, Any>>
        val firstItem = items.find { (it["name"] as String) == "Chicken Inasal Paa" }
        assertNotNull(firstItem, "Summary should include Chicken Inasal Paa (the first item)")
    }

    @Test
    fun `item subtotals are correctly calculated`() {
        // Order 1, item: Chicken Inasal Paa x2 at ₱149 = ₱298.00
        val summary = OrderService.getOrderSummary("order-1")
        assertNotNull(summary)
        val items = summary["items"] as List<Map<String, Any>>
        val chicken = items.find { (it["name"] as String) == "Chicken Inasal Paa" }
        assertNotNull(chicken, "Should include Chicken Inasal Paa")
        assertEquals("298.00", chicken["subtotal"], "2 x ₱149.00 = ₱298.00")
    }

    @Test
    fun `grand total is correctly calculated and formatted`() {
        // Order 1: (149×2)+(89×1)+(25×2) = 298+89+50 = 437
        val summary = OrderService.getOrderSummary("order-1")
        assertNotNull(summary)
        assertEquals("437.00", summary["grandTotal"], "Grand total should be 437.00")
    }

    @Test
    fun `prices are formatted with two decimal places`() {
        val summary = OrderService.getOrderSummary("order-1")
        assertNotNull(summary)
        val items = summary["items"] as List<Map<String, Any>>
        for (item in items) {
            val unitPrice = item["unitPrice"] as String
            val subtotal = item["subtotal"] as String
            assertTrue(unitPrice.matches(Regex("\\d+\\.\\d{2}")), "Unit price should have 2 decimal places: $unitPrice")
            assertTrue(subtotal.matches(Regex("\\d+\\.\\d{2}")), "Subtotal should have 2 decimal places: $subtotal")
        }
    }

    @Test
    fun `nonexistent order returns null`() {
        val summary = OrderService.getOrderSummary("nonexistent")
        assertNull(summary, "Should return null for nonexistent order")
    }

    @Test
    fun `summary for order with many items`() {
        // Order 2: Chickenjoy Bucket x1 + Jolly Spaghetti x2 + Peach Mango Pie x3
        // = 449 + 150 + 117 = 716
        val summary = OrderService.getOrderSummary("order-2")
        assertNotNull(summary)
        assertEquals("716.00", summary["grandTotal"], "Grand total should be 716.00")
        val items = summary["items"] as List<*>
        assertEquals(3, items.size, "Order 2 has 3 line items")
    }
}
