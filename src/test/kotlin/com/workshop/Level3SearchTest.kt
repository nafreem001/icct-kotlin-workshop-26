package com.workshop

import com.workshop.services.SearchService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Level 3A: Restaurant Search Tests
 *
 * Run with: ./gradlew test --tests "com.workshop.Level3SearchTest"
 *
 * Tests verify search results without prescribing implementation.
 */
class Level3SearchTest {

    @Test
    fun `search by cuisine Filipino returns Filipino restaurants`() {
        val results = SearchService.searchRestaurants(cuisine = "Filipino")
        assertTrue(results.isNotEmpty(), "Should find Filipino restaurants")
        assertTrue(
            results.all { it.cuisine == "Filipino" },
            "All results should be Filipino cuisine"
        )
    }

    @Test
    fun `search by cuisine is case insensitive`() {
        val results = SearchService.searchRestaurants(cuisine = "filipino")
        assertTrue(results.isNotEmpty(), "Should find Filipino restaurants with lowercase query")
    }

    @Test
    fun `search with minimum rating filter`() {
        val results = SearchService.searchRestaurants(minRating = 4.5)
        assertTrue(results.isNotEmpty(), "Should find high-rated restaurants")
        assertTrue(
            results.all { it.rating >= 4.5 },
            "All results should have rating >= 4.5"
        )
    }

    @Test
    fun `search sorted by rating descending`() {
        val results = SearchService.searchRestaurants(sortBy = "rating")
        assertTrue(results.size > 1, "Should return multiple restaurants")
        for (i in 0 until results.size - 1) {
            assertTrue(
                results[i].rating >= results[i + 1].rating,
                "Results should be sorted by rating descending"
            )
        }
    }

    @Test
    fun `search with max delivery time filter`() {
        val results = SearchService.searchRestaurants(maxDeliveryTime = 25)
        assertTrue(results.isNotEmpty(), "Should find restaurants with quick delivery")
        assertTrue(
            results.all { it.deliveryTimeMinutes <= 25 },
            "All results should deliver in 25 min or less"
        )
    }

    @Test
    fun `combined filters - Filipino cuisine with high rating`() {
        val results = SearchService.searchRestaurants(cuisine = "Filipino", minRating = 4.5)
        assertTrue(results.isNotEmpty(), "Should find high-rated Filipino restaurants")
        assertTrue(results.all { it.cuisine == "Filipino" && it.rating >= 4.5 })
    }

    @Test
    fun `search with no filters returns all restaurants`() {
        val results = SearchService.searchRestaurants()
        assertEquals(5, results.size, "Should return all 5 restaurants")
    }

    @Test
    fun `search for nonexistent cuisine returns empty`() {
        val results = SearchService.searchRestaurants(cuisine = "Martian")
        assertTrue(results.isEmpty(), "Should return empty for nonexistent cuisine")
    }
}
