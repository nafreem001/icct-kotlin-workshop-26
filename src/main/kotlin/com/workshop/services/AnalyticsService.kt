package com.workshop.services

import com.workshop.data.SampleData
import kotlinx.serialization.Serializable

object AnalyticsService {

    // Exercise 3B: Implement this method (see AnalyticsSummary data class below for required fields)
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
