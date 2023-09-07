package com.example.vicoba.data.models.keys

import kotlinx.serialization.Serializable

/** A key for the location of the user */
@Serializable
data class UserIDAddressID(
    val userID: Int,
    val userAddressID: Int
)

