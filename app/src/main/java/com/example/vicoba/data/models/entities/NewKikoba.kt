package com.example.vicoba.data.models.entities

import kotlinx.serialization.Serializable

/** A data class that holds information of the new kikoba */
@Serializable
data class NewKikoba(
    val kikobaName:String = "",
    val kikobaDesc:String = "",
    val kikobaOwnerID:Int = 0,
    val kikobaLocationID:String = ""
)
