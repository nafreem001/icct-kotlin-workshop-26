package com.workshop.services

import com.workshop.data.SampleData
import com.workshop.models.Restaurant

object SearchService {

    // =====================================================
    // LEVEL 3A EXERCISE: Implement searchRestaurants()
    // =====================================================
    //
    // Build a restaurant search that supports filtering and sorting.
    //
    // Parameters:
    //   cuisine    - If set, only return restaurants matching this cuisine (case-insensitive)
    //   minRating  - If set, only return restaurants with rating >= this value
    //   maxDeliveryTime - If set, only return restaurants with deliveryTimeMinutes <= this value
    //   sortBy     - Sort results by: "rating", "deliveryTime", "name", or "minimumOrder"
    //
    // Useful Kotlin collection functions:
    //   .filter { ... }         - keep items matching condition
    //   .sortedBy { ... }       - sort ascending by property
    //   .sortedByDescending { } - sort descending by property
    //   .lowercase()            - convert string to lowercase for comparison
    //
    // Example chain:
    //   restaurants
    //       .filter { it.cuisine.lowercase() == cuisine.lowercase() }
    //       .filter { it.rating >= minRating }
    //       .sortedByDescending { it.rating }
    //
    // There's no single "right" implementation - use whatever combination
    // of collection operations achieves the correct result!
    //
    fun searchRestaurants(
        cuisine: String? = null,
        minRating: Double? = null,
        maxDeliveryTime: Int? = null,
        sortBy: String? = null
    ): List<Restaurant> {
        // TODO: Replace this with your implementation
        return emptyList()
    }
}
