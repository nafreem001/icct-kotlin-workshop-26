package com.workshop

import com.workshop.models.LoyaltyTier
import com.workshop.services.LoyaltyService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Level 2C: Customer Loyalty Tier Tests
 *
 * Run with: ./gradlew test --tests "com.workshop.Level2LoyaltyTest"
 *
 * Tests verify:
 *   1. Loyalty tier is calculated based on order count and total spend
 *   2. getLoyaltyInfo() returns complete customer info
 *   3. Nonexistent customer returns null
 */
class Level2LoyaltyTest {

    @Test
    fun `customer with orders gets a loyalty tier`() {
        // cust-1 has 2 orders (order-1 and order-3)
        val tier = LoyaltyService.calculateTier("cust-1")
        assertNotNull(tier, "Should return a tier for valid customer")
        assertTrue(tier is LoyaltyTier, "Should be a LoyaltyTier instance")
    }

    @Test
    fun `customer tier uses when expression correctly`() {
        // cust-1 has 2 orders, should be Bronze (under 3 orders, under ₱1500 total)
        // Order 1: (149*2)+(89*1)+(25*2) = 437
        // Order 3: (395*1)+(245*1)+(155*2) = 950
        // Total: 1387 — still under ₱1500 with 2 orders, so Bronze
        val tier = LoyaltyService.calculateTier("cust-1")
        assertNotNull(tier)
        assertEquals("Bronze", tier.tierName, "cust-1 has 2 orders totaling ~₱1387, should be Bronze")
    }

    @Test
    fun `customer with more orders gets higher tier`() {
        // cust-2 has 3 orders (order-2, order-4, order-6)
        // That's >= 3 orders, so should be at least Silver
        val tier = LoyaltyService.calculateTier("cust-2")
        assertNotNull(tier)
        assertTrue(
            tier is LoyaltyTier.Silver || tier is LoyaltyTier.Gold || tier is LoyaltyTier.Platinum,
            "cust-2 has 3 orders, should be at least Silver but got ${tier.tierName}"
        )
    }

    @Test
    fun `loyalty info includes all fields`() {
        val info = LoyaltyService.getLoyaltyInfo("cust-1")
        assertNotNull(info, "Should return info for valid customer")
        assertEquals("cust-1", info.customerId)
        assertEquals("Juan dela Cruz", info.customerName)
        assertNotNull(info.tier)
        assertTrue(info.orderCount > 0, "Should have at least one order")
        assertTrue(info.totalSpend > 0.0, "Should have non-zero spend")
    }

    @Test
    fun `discount percent matches tier`() {
        val info = LoyaltyService.getLoyaltyInfo("cust-2")
        assertNotNull(info)
        assertEquals(info.tier.discountPercent, info.discountPercent,
            "Discount percent should match the tier's discount")
    }

    @Test
    fun `nonexistent customer returns null`() {
        val tier = LoyaltyService.calculateTier("nonexistent")
        assertNull(tier, "Should return null for nonexistent customer")
    }

    @Test
    fun `nonexistent customer info returns null`() {
        val info = LoyaltyService.getLoyaltyInfo("nonexistent")
        assertNull(info, "Should return null for nonexistent customer")
    }
}
