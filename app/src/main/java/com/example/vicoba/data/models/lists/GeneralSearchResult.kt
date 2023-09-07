package com.example.vicoba.data.models.lists

import kotlinx.serialization.Serializable

@Serializable
data class GeneralSearchResult(
    val firstName:String,
    val lastName:String,
    val kikobaName:String
)
