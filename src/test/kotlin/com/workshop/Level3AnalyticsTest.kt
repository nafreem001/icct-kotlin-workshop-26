package com.workshop

import com.workshop.services.AnalyticsService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Level 3B: Analytics Summary Tests
 *
 * Run with: ./gradlew test --tests "com.workshop.Level3AnalyticsTest"
 *
 * Tests verify the analytics summary without prescribing implementation.
 */
class Level3AnalyticsTest {

    @Test
    fun `total orders count is correct`() {
        val summary = AnalyticsService.getAnalyticsSummary()
        assertEquals(6, summary.totalOrders, "Should count all 6 orders")
    }

    @Test
    fun `total revenue is calculated correctly`() {
        // Order 1: (149×2)+(89×1)+(25×2) = 437
        // Order 2: (449×1)+(75×2)+(39×3) = 716
        // Order 3: (395×1)+(245×1)+(155×2) = 950
        // Order 4: (285×1)+(145×1)+(95×2) = 620
        // Order 5: (390×1)+(180×1) = 570
        // Order 6: (99×3)+(79×1)+(25×3) = 451
        // Total = 437 + 716 + 950 + 620 + 570 + 451 = 3744
        val summary = AnalyticsService.getAnalyticsSummary()
        assertEquals(3744.0, summary.totalRevenue, 0.01, "Total revenue should be ₱3,744")
    }

    @Test
    fun `average order value is correct`() {
        val summary = AnalyticsService.getAnalyticsSummary()
        assertEquals(624.0, summary.averageOrderValue, 0.01, "Average should be ₱624")
    }

    @Test
    fun `orders by status has all statuses`() {
        val summary = AnalyticsService.getAnalyticsSummary()
        assertTrue(summary.ordersByStatus.isNotEmpty(), "Should have status breakdown")
        assertEquals(2, summary.ordersByStatus["Placed"], "Should have 2 Placed orders")
        assertEquals(1, summary.ordersByStatus["Preparing"], "Should have 1 Preparing order")
        assertEquals(1, summary.ordersByStatus["Out for Delivery"], "Should have 1 OutForDelivery order")
        assertEquals(1, summary.ordersByStatus["Delivered"], "Should have 1 Delivered order")
        assertEquals(1, summary.ordersByStatus["Cancelled"], "Should have 1 Cancelled order")
    }

    @Test
    fun `top items are populated`() {
        val summary = AnalyticsService.getAnalyticsSummary()
        assertTrue(summary.topItems.isNotEmpty(), "Should have top items")
        assertTrue(summary.topItems.size <= 5, "Should have at most 5 top items")
        // Extra Rice and Pork BBQ are ordered 3 times each (by quantity)
        val topItemNames = summary.topItems.map { it.name }
        assertTrue(
            "Extra Rice" in topItemNames || "Pork BBQ" in topItemNames || "Peach Mango Pie" in topItemNames,
            "Top items should include frequently ordered items"
        )
    }

    @Test
    fun `revenue by restaurant is populated`() {
        val summary = AnalyticsService.getAnalyticsSummary()
        assertTrue(summary.revenueByRestaurant.isNotEmpty(), "Should have revenue by restaurant")
        // Mang Inasal has orders 1 and 6: 437 + 451 = 888
        assertTrue(
            summary.revenueByRestaurant.values.any { it > 0 },
            "Should have non-zero revenue"
        )
    }
}
