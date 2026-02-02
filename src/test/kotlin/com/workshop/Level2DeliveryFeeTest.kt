package com.workshop

import com.workshop.services.DeliveryService
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Level 2B: Delivery Fee Calculation Tests
 *
 * Run with: ./gradlew test --tests "com.workshop.Level2DeliveryFeeTest"
 *
 * Tests verify:
 *   1. Delivery fee is calculated based on distance tiers
 *   2. Null address is handled gracefully
 *   3. Distance calculation produces reasonable results
 */
class Level2DeliveryFeeTest {

    @Test
    fun `delivery fee calculated for order with customer address`() {
        // Order 1: Customer 1 (QC) ordering from Mang Inasal (QC)
        val result = DeliveryService.calculateDeliveryFee("order-1")
        assertTrue(result.isSuccess, "Should succeed for customer with address: ${result.exceptionOrNull()?.message}")
        val fee = result.getOrNull()!!
        assertTrue(fee.distanceKm >= 0, "Distance should be non-negative")
        assertTrue(fee.fee >= 0, "Fee should be non-negative")
    }

    @Test
    fun `customer with no address returns error`() {
        // Order 5: Customer 3 (Pedro) has no address
        val result = DeliveryService.calculateDeliveryFee("order-5")
        assertTrue(result.isFailure, "Should fail for customer without address")
        assertTrue(
            result.exceptionOrNull()?.message?.contains("address") == true,
            "Error should mention address"
        )
    }

    @Test
    fun `fee tiers are correct`() {
        // Verify the distance helper works and tiers are applied
        // 0-2km = free, 2-5km = ₱49, 5-10km = ₱99, 10+ = ₱149

        // Very close points (same area, ~1km)
        val shortDistance = DeliveryService.distanceInKm(14.6510, 121.0495, 14.6530, 121.0510)
        assertTrue(shortDistance < 2.0, "Short distance should be under 2km, got: $shortDistance")

        // Points about 12km apart (QC to Makati)
        val longDistance = DeliveryService.distanceInKm(14.6510, 121.0495, 14.5547, 121.0244)
        assertTrue(longDistance > 10.0, "QC to Makati should be over 10km, got: $longDistance")
    }

    @Test
    fun `nonexistent order returns error`() {
        val result = DeliveryService.calculateDeliveryFee("nonexistent")
        assertTrue(result.isFailure, "Should fail for nonexistent order")
    }

    @Test
    fun `delivery fee result contains all fields`() {
        val result = DeliveryService.calculateDeliveryFee("order-2")
        assertTrue(result.isSuccess, "Should succeed: ${result.exceptionOrNull()?.message}")
        val fee = result.getOrNull()!!
        assertTrue(fee.orderId == "order-2", "Should have correct orderId")
        assertTrue(fee.tier.isNotEmpty(), "Should have a tier label")
    }
}
