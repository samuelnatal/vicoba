package com.example.vicoba.data.models.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvitedKikoba(
    val kikobaID:Int = 0,
    val kikobaName:String = "",
    val kikobaDesc:String = "",
    val createdAt:String = "",
    @SerialName(value ="firstName")
    val ownerFirstName:String = "",
    @SerialName(value ="lastName")
    val ownerLastName:String = "",
    val kikobaAdmin:Int = 0,
    val region:String = "",
    val district:String = "",
    val ward:String = "",
)
