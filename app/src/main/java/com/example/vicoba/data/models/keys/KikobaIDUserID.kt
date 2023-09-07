package com.example.vicoba.data.models.keys

import kotlinx.serialization.Serializable

@Serializable
data class KikobaIDUserID(
    val userID: Int,
    val kikobaID:Int
)
