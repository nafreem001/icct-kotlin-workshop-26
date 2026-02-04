package com.workshop.services

import com.workshop.data.SampleData
import com.workshop.models.LoyaltyTier
import kotlinx.serialization.Serializable

object LoyaltyService {

    // Exercise 2C: Implement this method
    // Use a `when` expression to determine the loyalty tier based on order count and total spend.
    // Look at LoyaltyTier.kt to see the available tiers and their properties.
    // Look at the test file to understand the tier thresholds.
    fun calculateTier(customerId: String): LoyaltyTier? {
        SampleData.customers.find { it.id == customerId } ?: return null

        // TODO: 1. Get all orders for this customer from SampleData.orders
        // TODO: 2. Calculate the order count and total spend
        // TODO: 3. Use a `when` expression to return the appropriate tier
        return null
    }

    // Exercise 2C: Implement this method
    // Return a LoyaltyInfo object with the customer's name, tier, order count, total spend, and discount.
    fun getLoyaltyInfo(customerId: String): LoyaltyInfo? {
        val customer = SampleData.customers.find { it.id == customerId } ?: return null

        // TODO: 1. Call calculateTier() to get the tier
        // TODO: 2. Calculate order count and total spend
        // TODO: 3. Return a LoyaltyInfo with all fields populated
        return null
    }
}

@Serializable
data class LoyaltyInfo(
    val customerId: String,
    val customerName: String,
    val tier: LoyaltyTier,
    val orderCount: Int,
    val totalSpend: Double,
    val discountPercent: Int
)
