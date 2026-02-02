package com.workshop.models

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: String,
    val name: String,
    val cuisine: String,
    val rating: Double,
    val address: String,
    val deliveryTimeMinutes: Int,
    val minimumOrder: Double,
    val menu: List<MenuItem> = emptyList()
)

@Serializable
data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val dietaryTags: List<String>? = null,
    val isAvailable: Boolean = true
)
