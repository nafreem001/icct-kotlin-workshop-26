package com.workshop

import com.workshop.services.MenuService
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Level 1C: Restaurant Hours Tests
 *
 * Run with: ./gradlew test --tests "com.workshop.Level1RestaurantHoursTest"
 *
 * These tests verify that MenuService.isOpenAt() works correctly.
 * Watch out for:
 *   - Restaurants with no operating hours (should be treated as always open)
 *   - Late-night restaurants whose hours cross midnight
 */
class Level1RestaurantHoursTest {

    @Test
    fun `restaurant with normal hours is open during business hours`() {
        // Mang Inasal (resto-1): 09:00 - 21:00
        val result = MenuService.isOpenAt("resto-1", "14:30")
        assertTrue(result.isSuccess, "Should succeed for valid restaurant")
        assertTrue(result.getOrNull() == true, "Should be open at 14:30 (hours: 09:00-21:00)")
    }

    @Test
    fun `restaurant with normal hours is closed before opening`() {
        // Mang Inasal (resto-1): 09:00 - 21:00
        val result = MenuService.isOpenAt("resto-1", "07:00")
        assertTrue(result.isSuccess, "Should succeed for valid restaurant")
        assertTrue(result.getOrNull() == false, "Should be closed at 07:00 (opens at 09:00)")
    }

    @Test
    fun `restaurant with normal hours is closed after closing`() {
        // Mang Inasal (resto-1): 09:00 - 21:00
        val result = MenuService.isOpenAt("resto-1", "22:00")
        assertTrue(result.isSuccess, "Should succeed for valid restaurant")
        assertTrue(result.getOrNull() == false, "Should be closed at 22:00 (closes at 21:00)")
    }

    @Test
    fun `late-night restaurant is open before midnight`() {
        // Ramen Nagi (resto-4): 22:00 - 02:00
        val result = MenuService.isOpenAt("resto-4", "23:00")
        assertTrue(result.isSuccess, "Should succeed for valid restaurant")
        assertTrue(result.getOrNull() == true, "Should be open at 23:00 (hours: 22:00-02:00)")
    }

    @Test
    fun `late-night restaurant is open just after midnight`() {
        // Ramen Nagi (resto-4): 22:00 - 02:00
        val result = MenuService.isOpenAt("resto-4", "00:30")
        assertTrue(result.isSuccess, "Should succeed for valid restaurant")
        assertTrue(result.getOrNull() == true, "Should be open at 00:30 (hours: 22:00-02:00)")
    }

    @Test
    fun `late-night restaurant is open after midnight`() {
        // Ramen Nagi (resto-4): 22:00 - 02:00
        val result = MenuService.isOpenAt("resto-4", "01:00")
        assertTrue(result.isSuccess, "Should succeed for valid restaurant")
        assertTrue(result.getOrNull() == true, "Should be open at 01:00 (hours: 22:00-02:00)")
    }

    @Test
    fun `late-night restaurant is closed during the day`() {
        // Ramen Nagi (resto-4): 22:00 - 02:00
        val result = MenuService.isOpenAt("resto-4", "15:00")
        assertTrue(result.isSuccess, "Should succeed for valid restaurant")
        assertTrue(result.getOrNull() == false, "Should be closed at 15:00 (hours: 22:00-02:00)")
    }

    @Test
    fun `restaurant with no hours is always open`() {
        // Locavore (resto-5): no operating hours set
        val result = MenuService.isOpenAt("resto-5", "03:00")
        assertTrue(result.isSuccess, "Should succeed for valid restaurant")
        assertTrue(result.getOrNull() == true, "Restaurant with no hours should be treated as always open")
    }

    @Test
    fun `nonexistent restaurant returns failure`() {
        val result = MenuService.isOpenAt("nonexistent", "12:00")
        assertTrue(result.isFailure, "Should fail for nonexistent restaurant")
    }
}
