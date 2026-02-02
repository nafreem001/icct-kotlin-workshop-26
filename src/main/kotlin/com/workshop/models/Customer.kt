package com.workshop.models

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: Address? = null
)

@Serializable
data class Address(
    val street: String,
    val city: String,
    val latitude: Double,
    val longitude: Double
)
