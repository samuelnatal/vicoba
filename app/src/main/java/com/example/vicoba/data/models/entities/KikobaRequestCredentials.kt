package com.example.vicoba.data.models.entities

import kotlinx.serialization.Serializable

/** A container for holding credentials needed for request to join kikoba */
@Serializable
data class KikobaRequestCredentials(
    val userID:Int,
    val kikobaID:Int
)
