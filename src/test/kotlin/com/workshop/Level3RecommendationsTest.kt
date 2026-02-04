package com.workshop

import com.workshop.services.RecommendationService
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Level 3C: Order Recommendations Tests
 *
 * Run with: ./gradlew test --tests "com.workshop.Level3RecommendationsTest"
 *
 * Tests verify:
 *   1. Recommendations are returned for customers with order history
 *   2. Recommended items are NOT items the customer has already ordered
 *   3. Recommendations are limited to the requested count
 */
class Level3RecommendationsTest {

    @Test
    fun `customer with orders gets recommendations`() {
        // cust-1 has ordered from resto-1 and resto-3
        val recs = RecommendationService.getRecommendations("cust-1")
        assertTrue(recs.isNotEmpty(), "Should recommend items for customer with order history")
    }

    @Test
    fun `recommendations do not include already ordered items`() {
        // cust-1 ordered: mi-1, mi-4, mi-6 (from resto-1), mesa-1, mesa-3, mesa-6 (from resto-3)
        val orderedIds = setOf("mi-1", "mi-4", "mi-6", "mesa-1", "mesa-3", "mesa-6")
        val recs = RecommendationService.getRecommendations("cust-1")
        for (rec in recs) {
            assertTrue(
                rec.menuItemId !in orderedIds,
                "Should not recommend already-ordered item: ${rec.name} (${rec.menuItemId})"
            )
        }
    }

    @Test
    fun `recommendations respect limit`() {
        val recs = RecommendationService.getRecommendations("cust-1", limit = 3)
        assertTrue(recs.size <= 3, "Should return at most 3 recommendations, got ${recs.size}")
    }

    @Test
    fun `recommendations include restaurant name`() {
        val recs = RecommendationService.getRecommendations("cust-1")
        for (rec in recs) {
            assertTrue(rec.restaurantName.isNotEmpty(), "Each recommendation should include restaurant name")
        }
    }

    @Test
    fun `recommendations include a reason`() {
        val recs = RecommendationService.getRecommendations("cust-1")
        for (rec in recs) {
            assertTrue(rec.reason.isNotEmpty(), "Each recommendation should include a reason")
        }
    }

    @Test
    fun `customer with no orders gets empty recommendations`() {
        // cust-3 has orders but let's test with a truly nonexistent customer
        val recs = RecommendationService.getRecommendations("nonexistent")
        assertTrue(recs.isEmpty(), "Nonexistent customer should get no recommendations")
    }

    @Test
    fun `recommendations have valid prices`() {
        val recs = RecommendationService.getRecommendations("cust-2")
        for (rec in recs) {
            assertTrue(rec.price > 0, "Recommended item price should be positive: ${rec.name} = ${rec.price}")
        }
    }
}
