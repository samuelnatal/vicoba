package com.example.vicoba.data.models.entities

import kotlinx.serialization.Serializable

@Serializable
data class EditedKikobaProfile(
    val kikobaID: Int = 0,
    val kikobaName:String = "",
    val kikobaDesc:String = "",
    val kikobaLocationID:String = "",
    val sharePrice:String = "",
    val maxShare:String = "",
    val shareCircle:String = "",
)
