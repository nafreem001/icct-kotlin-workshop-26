package com.workshop.services

import com.workshop.data.SampleData
import com.workshop.models.MenuItem
import com.workshop.models.Restaurant

object MenuService {

    fun getRestaurants(): List<Restaurant> = SampleData.restaurants

    fun getRestaurantById(id: String): Restaurant? =
        SampleData.restaurants.find { it.id == id }

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

    fun isOpenAt(restaurantId: String, time: String): Result<Boolean> {
        val restaurant = getRestaurantById(restaurantId)
            ?: return Result.failure(Exception("Restaurant not found: $restaurantId"))

        val hours = restaurant.operatingHours!!

        val queryMinutes = timeToMinutes(time)
        val openMinutes = timeToMinutes(hours.open)
        val closeMinutes = timeToMinutes(hours.close)

        val isOpen = queryMinutes in openMinutes until closeMinutes

        return Result.success(isOpen)
    }

    private fun timeToMinutes(time: String): Int {
        val parts = time.split(":")
        return parts[0].toInt() * 60 + parts[1].toInt()
    }
}
