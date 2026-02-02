package com.workshop.services

import com.workshop.data.SampleData
import kotlinx.serialization.Serializable

object AnalyticsService {

    // =====================================================
    // LEVEL 3B EXERCISE: Implement getAnalyticsSummary()
    // =====================================================
    //
    // Build an analytics dashboard that aggregates order data.
    //
    // Required fields in the response (see AnalyticsSummary below):
    //   totalRevenue       - Sum of all order totals (price × quantity for each item)
    //   totalOrders        - Total number of orders
    //   averageOrderValue  - totalRevenue / totalOrders
    //   ordersByStatus     - Map of status name → count (e.g., "Placed" → 2)
    //   topItems           - Top 5 most ordered items by total quantity
    //   revenueByRestaurant - Map of restaurant name → total revenue
    //
    // Useful Kotlin collection functions:
    //   .sumOf { ... }              - sum a numeric property
    //   .groupBy { ... }            - group into Map<Key, List<Value>>
    //   .flatMap { ... }            - flatten nested lists
    //   .sortedByDescending { ... } - sort high to low
    //   .take(n)                    - take first n items
    //   .mapValues { ... }          - transform map values
    //   .associate { ... }          - create a map from a list
    //
    // Example: Group orders by status
    //   orders.groupBy { it.status.displayName }
    //         .mapValues { (_, orders) -> orders.size }
    //
    // Example: Find top items
    //   orders.flatMap { it.items }
    //         .groupBy { it.menuItemName }
    //         .mapValues { (_, items) -> items.sumOf { it.quantity } }
    //         .entries.sortedByDescending { it.value }
    //         .take(5)
    //         .map { TopItem(it.key, it.value) }
    //
    fun getAnalyticsSummary(): AnalyticsSummary {
        // TODO: Replace this with your implementation
        // For now, return empty/zero values
        return AnalyticsSummary(
            totalRevenue = 0.0,
            totalOrders = 0,
            averageOrderValue = 0.0,
            ordersByStatus = emptyMap(),
            topItems = emptyList(),
            revenueByRestaurant = emptyMap()
        )
    }
}

@Serializable
data class AnalyticsSummary(
    val totalRevenue: Double,
    val totalOrders: Int,
    val averageOrderValue: Double,
    val ordersByStatus: Map<String, Int>,
    val topItems: List<TopItem>,
    val revenueByRestaurant: Map<String, Double>
)

@Serializable
data class TopItem(
    val name: String,
    val totalQuantity: Int
)
