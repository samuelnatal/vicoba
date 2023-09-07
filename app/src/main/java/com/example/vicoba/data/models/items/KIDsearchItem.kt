package com.example.vicoba.data.models.items

import kotlinx.serialization.Serializable

/** Kikoba ID and item to search search key*/
@Serializable
data class KIDsearchItem(
    val kikobaID: Int,
    val searchItem: String
)
