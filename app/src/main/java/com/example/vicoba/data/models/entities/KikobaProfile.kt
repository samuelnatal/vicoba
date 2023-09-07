package com.example.vicoba.data.models.entities

import kotlinx.serialization.Serializable

@Serializable
data class KikobaProfile(
    val kikobaID:Int = 0,
    val kikobaName:String = "",
    val kikobaDesc:String = "",
    val region:String = "",
    val district:String = "",
    val ward:String = ""
)
