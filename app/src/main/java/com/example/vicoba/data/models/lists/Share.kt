package com.example.vicoba.data.models.lists

import kotlinx.serialization.Serializable

@Serializable
data class Share(
    val ownerID:Int,
    val ownerFirstName:String,
    val ownerLastName:String,
    val shareAmount:Int,
    val date:String
)
