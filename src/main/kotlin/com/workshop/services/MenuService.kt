package com.workshop.services

import com.workshop.data.SampleData
import com.workshop.models.MenuItem
import com.workshop.models.Restaurant

object MenuService {

    fun getRestaurants(): List<Restaurant> = SampleData.restaurants

    fun getRestaurantById(id: String): Restaurant? =
        SampleData.restaurants.find { it.id == id }

    /**
     * Filters the menu of a restaurant by optional price and dietary criteria.
     *
     * @param restaurantId The restaurant whose menu to filter
     * @param maxPrice If set, only return items at or below this price
     * @param dietary If set, only return items that have this dietary tag
     * @return Filtered list of menu items, or null if restaurant not found
     */
    fun filterMenu(restaurantId: String, maxPrice: Double?, dietary: String?): List<MenuItem>? {
        val restaurant = getRestaurantById(restaurantId) ?: return null
        var items = restaurant.menu.filter { it.isAvailable }

        // =====================================================
        // LEVEL 1A BUG #1: Price filter is wrong!
        // The filter should keep items where price <= maxPrice,
        // but it currently uses > instead of <=
        // =====================================================
        if (maxPrice != null) {
            items = items.filter { it.price > maxPrice }
        }

        // =====================================================
        // LEVEL 1A BUG #2: Dietary filter crashes on null!
        // Some menu items have null dietaryTags.
        // This code doesn't handle that case and will crash
        // with a NullPointerException.
        //
        // HINT: Use the ?. (safe call) operator or check for null
        // =====================================================
        if (dietary != null) {
            items = items.filter { it.dietaryTags!!.contains(dietary) }
        }

        return items
    }
}
