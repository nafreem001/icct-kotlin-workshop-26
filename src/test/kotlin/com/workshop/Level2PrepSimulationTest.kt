package com.workshop

import com.workshop.services.PrepService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Level 2D: Order Preparation Simulation Tests
 *
 * Run with: ./gradlew test --tests "com.workshop.Level2PrepSimulationTest"
 *
 * Tests verify:
 *   1. Preparation can be started for an order
 *   2. Progress is tracked correctly
 *   3. Items are prepared concurrently (not sequentially)
 */
class Level2PrepSimulationTest {

    @Test
    fun `start preparation returns progress`() {
        val scope = CoroutineScope(Dispatchers.Default)
        // Use order-3 to avoid conflict with concurrent test
        val result = PrepService.startPreparation("order-3", scope)
        assertTrue(result.isSuccess, "Should succeed for valid order: ${result.exceptionOrNull()?.message}")
        val progress = result.getOrNull()!!
        assertTrue(progress.totalItems > 0, "Should have items to prepare")
        assertTrue(progress.inProgress, "Should be in progress after starting")
    }

    @Test
    fun `nonexistent order fails`() {
        val scope = CoroutineScope(Dispatchers.Default)
        val result = PrepService.startPreparation("nonexistent", scope)
        assertTrue(result.isFailure, "Should fail for nonexistent order")
    }

    @Test
    fun `preparation completes with concurrent items`() = runBlocking {
        val scope = CoroutineScope(Dispatchers.Default)
        // Order 2: Chickenjoy Bucket x1 + Jolly Spaghetti x2 + Peach Mango Pie x3 = 6 items
        val result = PrepService.startPreparation("order-2", scope)
        assertTrue(result.isSuccess, "Should start preparation: ${result.exceptionOrNull()?.message}")

        val startTime = System.currentTimeMillis()

        // Wait for completion (should be ~500ms if concurrent, ~3000ms if sequential)
        var attempts = 0
        while (attempts < 30) {
            delay(100)
            val status = PrepService.getPrepStatus("order-2")
            assertNotNull(status, "Should have prep status")
            if (!status.inProgress) break
            attempts++
        }

        val elapsed = System.currentTimeMillis() - startTime
        val status = PrepService.getPrepStatus("order-2")
        assertNotNull(status)

        assertTrue(
            status.completedItems == status.totalItems,
            "All items should be completed. Got ${status.completedItems}/${status.totalItems}"
        )

        // If concurrent, 6 items at 500ms each should complete in ~500-1000ms, not 3000ms
        assertTrue(
            elapsed < 2500,
            "Preparation should be concurrent. 6 items took ${elapsed}ms (expected <2500ms if concurrent)"
        )
    }

    @Test
    fun `get status for order not being prepared returns null`() {
        val status = PrepService.getPrepStatus("order-never-started")
        assertTrue(status == null, "Should return null for order not being prepared")
    }
}
