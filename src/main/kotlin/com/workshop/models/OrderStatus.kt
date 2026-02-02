package com.workshop.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class OrderStatus {
    abstract val displayName: String

    @Serializable
    @SerialName("Placed")
    data object Placed : OrderStatus() {
        override val displayName = "Placed"
    }

    @Serializable
    @SerialName("Preparing")
    data object Preparing : OrderStatus() {
        override val displayName = "Preparing"
    }

    @Serializable
    @SerialName("OutForDelivery")
    data object OutForDelivery : OrderStatus() {
        override val displayName = "Out for Delivery"
    }

    @Serializable
    @SerialName("Delivered")
    data object Delivered : OrderStatus() {
        override val displayName = "Delivered"
    }

    @Serializable
    @SerialName("Cancelled")
    data object Cancelled : OrderStatus() {
        override val displayName = "Cancelled"
    }

    // =====================================================
    // LEVEL 2A EXERCISE: Implement canTransitionTo()
    // =====================================================
    // TODO: Implement this method to validate status transitions.
    //
    // Valid transitions:
    //   Placed     → Preparing, Cancelled
    //   Preparing  → OutForDelivery, Cancelled
    //   OutForDelivery → Delivered, Cancelled
    //   Delivered  → (no transitions allowed)
    //   Cancelled  → (no transitions allowed)
    //
    // HINT: Use a 'when' expression on 'this' to check the current status,
    //       then check if 'newStatus' is in the allowed set.
    //
    // Example 'when' syntax:
    //   when (this) {
    //       is Placed -> ...
    //       is Preparing -> ...
    //       ...
    //   }
    //
    // Return true if the transition is valid, false otherwise.
    fun canTransitionTo(newStatus: OrderStatus): Boolean {
        // TODO: Replace this with your implementation
        return false
    }
}
