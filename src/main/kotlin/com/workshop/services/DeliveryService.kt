package com.workshop.services

import com.workshop.data.SampleData
import com.workshop.models.Customer
import com.workshop.models.Order
import com.workshop.models.Restaurant
import kotlin.math.*

// Exercise 2B: Implement these extension functions

// Extension: look up the restaurant for an order
fun Order.findRestaurant(): Restaurant? {
    TODO("Implement: find the restaurant matching this order's restaurantId in SampleData.restaurants")
}

// Extension: look up the customer for an order
fun Order.findCustomer(): Customer? {
    TODO("Implement: find the customer matching this order's customerId in SampleData.customers")
}

// Extension: round a Double to N decimal places
fun Double.roundTo(decimals: Int): Double {
    TODO("Implement: round this Double to the given number of decimal places using 10.0.pow() and Math.round()")
}

object DeliveryService {

    // Exercise 2B: Implement this method
    fun calculateDeliveryFee(orderId: String): Result<DeliveryFeeResult> {
        return Result.failure(Exception("Not implemented yet! Complete Exercise 2B to calculate delivery fees."))
    }

    /**
     * Calculates distance between two points using the Haversine formula.
     * Returns distance in kilometers.
     *
     * You don't need to understand this formula - just call it with
     * (lat1, lon1) for the restaurant and (lat2, lon2) for the customer.
     */
    fun distanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadiusKm = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadiusKm * c
    }
}

@kotlinx.serialization.Serializable
data class DeliveryFeeResult(
    val orderId: String,
    val distanceKm: Double,
    val fee: Double,
    val tier: String
)
