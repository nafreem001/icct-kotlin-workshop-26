package com.workshop.services

import com.workshop.data.SampleData
import com.workshop.models.MenuItem
import kotlinx.serialization.Serializable

object RecommendationService {

    // Exercise 3C: Implement this method
    // Given a customer, analyze their past orders and recommend menu items they haven't tried.
    //
    // Useful collection operations: flatMap, groupBy, sortedByDescending, distinctBy, filter, map, toSet
    //
    // Approach:
    // 1. Find what the customer has already ordered (orderedItemIds)
    // 2. Find restaurants the customer has visited
    // 3. Get untried items from those restaurants
    // 4. Optionally: look at what similar customers ordered (customers who ordered the same items)
    // 5. Return up to `limit` recommendations
    fun getRecommendations(customerId: String, limit: Int = 5): List<RecommendedItem> {
        SampleData.customers.find {
            it.id == customerId
        } ?: return emptyList()

        return emptyList()
    }
}

@Serializable
data class RecommendedItem(
    val menuItemId: String,
    val name: String,
    val price: Double,
    val restaurantName: String,
    val reason: String
)
