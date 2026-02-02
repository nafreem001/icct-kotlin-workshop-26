package com.workshop

import com.workshop.services.OrderService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Level 1B: Order Total Calculation Tests
 *
 * Run with: ./gradlew test --tests "com.workshop.Level1OrderTotalTest"
 *
 * These tests verify that OrderService.calculateTotal() works correctly.
 * There are TWO bugs to fix:
 *   1. Price is not multiplied by quantity
 *   2. 10% discount for orders over ₱500 is not applied
 */
class Level1OrderTotalTest {

    @Test
    fun `order total should multiply price by quantity`() {
        // Order 1: 2x Chicken Inasal Paa (₱149) + 1x Halo-Halo (₱89) + 2x Extra Rice (₱25)
        // Subtotal = (149 × 2) + (89 × 1) + (25 × 2) = 298 + 89 + 50 = 437
        val total = OrderService.calculateTotal("order-1")
        assertNotNull(total, "Should return total for valid order")
        // Order 1 subtotal is ₱437, which is under ₱500, so no discount
        assertEquals(437.0, total, 0.01, "Order 1 total should be ₱437.00 (no discount, under ₱500)")
    }

    @Test
    fun `order over 500 should get 10 percent discount`() {
        // Order 2: 1x Chickenjoy Bucket (₱449) + 2x Jolly Spaghetti (₱75) + 3x Peach Mango Pie (₱39)
        // Subtotal = 449 + 150 + 117 = 716
        // Discount = 716 × 0.9 = 644.40
        val total = OrderService.calculateTotal("order-2")
        assertNotNull(total)
        assertEquals(644.40, total, 0.01, "Order 2 total should be ₱644.40 (₱716 with 10% discount)")
    }

    @Test
    fun `order 3 total with discount`() {
        // Order 3: 1x Sinigang (₱395) + 1x Laing (₱245) + 2x Bibingka (₱155)
        // Subtotal = 395 + 245 + 310 = 950
        // Discount = 950 × 0.9 = 855.00
        val total = OrderService.calculateTotal("order-3")
        assertNotNull(total)
        assertEquals(855.0, total, 0.01, "Order 3 total should be ₱855.00 (₱950 with 10% discount)")
    }

    @Test
    fun `order 4 total with discount`() {
        // Order 4: 1x Lechon Belly (₱285) + 1x Ensaladang Talong (₱145) + 2x Turon (₱95)
        // Subtotal = 285 + 145 + 190 = 620
        // Discount = 620 × 0.9 = 558.00
        val total = OrderService.calculateTotal("order-4")
        assertNotNull(total)
        assertEquals(558.0, total, 0.01, "Order 4 total should be ₱558.00 (₱620 with 10% discount)")
    }

    @Test
    fun `order 5 total with discount`() {
        // Order 5: 1x Original King Butao (₱390) + 1x Gyoza (₱180)
        // Subtotal = 390 + 180 = 570
        // Discount = 570 × 0.9 = 513.00
        val total = OrderService.calculateTotal("order-5")
        assertNotNull(total)
        assertEquals(513.0, total, 0.01, "Order 5 total should be ₱513.00 (₱570 with 10% discount)")
    }

    @Test
    fun `nonexistent order returns null`() {
        val total = OrderService.calculateTotal("nonexistent")
        assertNull(total, "Should return null for invalid order")
    }
}
