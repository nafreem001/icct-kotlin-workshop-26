package com.workshop.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object LoyaltyTierSerializer : KSerializer<LoyaltyTier> {
    override val descriptor = PrimitiveSerialDescriptor("LoyaltyTier", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LoyaltyTier) {
        encoder.encodeString(value.tierName)
    }

    override fun deserialize(decoder: Decoder): LoyaltyTier {
        return when (decoder.decodeString()) {
            "Bronze" -> LoyaltyTier.Bronze
            "Silver" -> LoyaltyTier.Silver
            "Gold" -> LoyaltyTier.Gold
            "Platinum" -> LoyaltyTier.Platinum
            else -> throw IllegalArgumentException("Unknown LoyaltyTier")
        }
    }
}

@Serializable(with = LoyaltyTierSerializer::class)
sealed class LoyaltyTier {
    abstract val tierName: String
    abstract val discountPercent: Int
    abstract val minimumOrders: Int

    data object Bronze : LoyaltyTier() {
        override val tierName = "Bronze"
        override val discountPercent = 0
        override val minimumOrders = 0
    }

    data object Silver : LoyaltyTier() {
        override val tierName = "Silver"
        override val discountPercent = 5
        override val minimumOrders = 3
    }

    data object Gold : LoyaltyTier() {
        override val tierName = "Gold"
        override val discountPercent = 10
        override val minimumOrders = 10
    }

    data object Platinum : LoyaltyTier() {
        override val tierName = "Platinum"
        override val discountPercent = 15
        override val minimumOrders = 25
    }
}
