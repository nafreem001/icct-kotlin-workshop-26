package com.workshop.services

import com.workshop.data.SampleData
import com.workshop.models.Order
import kotlin.math.*

object DeliveryService {

    // =====================================================
    // LEVEL 2B EXERCISE: Implement calculateDeliveryFee()
    // =====================================================
    // TODO: Calculate the delivery fee for an order based on distance.
    //
    // Steps:
    // 1. Find the order by ID (return error if not found)
    // 2. Find the customer by order.customerId
    // 3. Check if the customer has an address (customer.address)
    //    - If address is null, return an error: "Customer has no delivery address on file"
    //    HINT: Use ?. and ?: (Elvis operator) for null safety
    // 4. Get the restaurant coordinates from SampleData.restaurantCoordinates
    // 5. Calculate the distance using the distanceInKm() helper below
    // 6. Determine the fee based on distance tiers using a 'when' expression:
    //    - 0 to 2 km:   Free (₱0)
    //    - 2 to 5 km:   ₱49
    //    - 5 to 10 km:  ₱99
    //    - Over 10 km:  ₱149
    //
    //    HINT for 'when' with ranges:
    //      when {
    //          distance <= 2.0 -> 0.0
    //          distance <= 5.0 -> 49.0
    //          ...
    //      }
    //
    // Return type is Result<DeliveryFeeResult>
    fun calculateDeliveryFee(orderId: String): Result<DeliveryFeeResult> {
        // TODO: Replace this with your implementation
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
