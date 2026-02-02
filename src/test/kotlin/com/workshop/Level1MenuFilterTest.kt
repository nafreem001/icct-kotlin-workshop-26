package com.workshop

import com.workshop.services.MenuService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Level 1A: Menu Filtering Tests
 *
 * Run with: ./gradlew test --tests "com.workshop.Level1MenuFilterTest"
 *
 * These tests verify that MenuService.filterMenu() works correctly.
 * There are TWO bugs to fix:
 *   1. Price filter uses wrong comparison operator
 *   2. Dietary filter doesn't handle null dietary tags
 */
class Level1MenuFilterTest {

    @Test
    fun `filter by max price should return items at or below the price`() {
        // Mang Inasal (resto-1) menu has items from ₱25 to ₱159
        val items = MenuService.filterMenu("resto-1", maxPrice = 100.0, dietary = null)
        assertNotNull(items, "Should return items for valid restaurant")
        assertTrue(items.isNotEmpty(), "Should have items under ₱100")
        assertTrue(
            items.all { it.price <= 100.0 },
            "All items should be ₱100 or less, but got prices: ${items.map { "${it.name}: ₱${it.price}" }}"
        )
    }

    @Test
    fun `filter by max price 200 on Mesa should include items up to 200`() {
        // Mesa (resto-3) has Laing at ₱245, Sizzling Tofu at ₱195, Bibingka at ₱155, Leche Flan at ₱175
        val items = MenuService.filterMenu("resto-3", maxPrice = 200.0, dietary = null)
        assertNotNull(items)
        assertTrue(items.isNotEmpty(), "Should have items at or below ₱200")
        assertTrue(
            items.all { it.price <= 200.0 },
            "All items should be ₱200 or less"
        )
        assertTrue(
            items.any { it.name == "Sizzling Tofu" },
            "Should include Sizzling Tofu (₱195)"
        )
    }

    @Test
    fun `filter by dietary tag should not crash on items with null tags`() {
        // Jollibee (resto-2) has items with null dietary tags
        // This should NOT throw a NullPointerException
        val items = MenuService.filterMenu("resto-2", maxPrice = null, dietary = "vegetarian")
        assertNotNull(items, "Should return items for valid restaurant")
        assertTrue(
            items.all { it.dietaryTags?.contains("vegetarian") == true },
            "All returned items should have the 'vegetarian' tag"
        )
    }

    @Test
    fun `filter vegetarian items from Mang Inasal`() {
        val items = MenuService.filterMenu("resto-1", maxPrice = null, dietary = "vegetarian")
        assertNotNull(items)
        assertTrue(items.isNotEmpty(), "Mang Inasal has vegetarian items")
        // Halo-Halo and Extra Rice are vegetarian
        val names = items.map { it.name }
        assertTrue("Halo-Halo" in names, "Halo-Halo should be vegetarian")
        assertTrue("Extra Rice" in names, "Extra Rice should be vegetarian")
    }

    @Test
    fun `combined filter - vegetarian items under 100`() {
        val items = MenuService.filterMenu("resto-1", maxPrice = 100.0, dietary = "vegetarian")
        assertNotNull(items)
        assertTrue(items.isNotEmpty(), "Should have cheap vegetarian items")
        assertTrue(
            items.all { it.price <= 100.0 && it.dietaryTags?.contains("vegetarian") == true },
            "All items should be under ₱100 AND vegetarian"
        )
    }

    @Test
    fun `no filters returns all available items`() {
        val items = MenuService.filterMenu("resto-1", maxPrice = null, dietary = null)
        assertNotNull(items)
        assertEquals(6, items.size, "Mang Inasal has 6 menu items")
    }

    @Test
    fun `invalid restaurant returns null`() {
        val items = MenuService.filterMenu("nonexistent", maxPrice = null, dietary = null)
        assertEquals(null, items, "Should return null for invalid restaurant")
    }
}
