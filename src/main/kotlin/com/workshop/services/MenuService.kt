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

        if (maxPrice != null) {
            items = items.filter { it.price > maxPrice }
        }

        if (dietary != null) {
            items = items.filter { it.dietaryTags!!.contains(dietary) }
        }

        return items
    }
}
