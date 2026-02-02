package com.workshop.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Custom serializer that converts OrderStatus to/from a plain string.
 * e.g. OrderStatus.Placed serializes as "Placed" (not {"type":"Placed"})
 */
object OrderStatusSerializer : KSerializer<OrderStatus> {
    override val descriptor = PrimitiveSerialDescriptor("OrderStatus", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OrderStatus) {
        encoder.encodeString(
            when (value) {
                is OrderStatus.Placed -> "Placed"
                is OrderStatus.Preparing -> "Preparing"
                is OrderStatus.OutForDelivery -> "OutForDelivery"
                is OrderStatus.Delivered -> "Delivered"
                is OrderStatus.Cancelled -> "Cancelled"
            }
        )
    }

    override fun deserialize(decoder: Decoder): OrderStatus {
        return when (decoder.decodeString()) {
            "Placed" -> OrderStatus.Placed
            "Preparing" -> OrderStatus.Preparing
            "OutForDelivery" -> OrderStatus.OutForDelivery
            "Delivered" -> OrderStatus.Delivered
            "Cancelled" -> OrderStatus.Cancelled
            else -> throw IllegalArgumentException("Unknown OrderStatus")
        }
    }
}

@Serializable(with = OrderStatusSerializer::class)
sealed class OrderStatus {
    abstract val displayName: String

    data object Placed : OrderStatus() {
        override val displayName = "Placed"
    }

    data object Preparing : OrderStatus() {
        override val displayName = "Preparing"
    }

    data object OutForDelivery : OrderStatus() {
        override val displayName = "Out for Delivery"
    }

    data object Delivered : OrderStatus() {
        override val displayName = "Delivered"
    }

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
