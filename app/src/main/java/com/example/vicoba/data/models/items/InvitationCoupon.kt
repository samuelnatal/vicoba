package com.example.vicoba.data.models.items

import kotlinx.serialization.Serializable

@Serializable
data class InvitationCoupon(
    val userID:Int,
    val kikobaID:Int,
    val userFirstName:String,
    val userLastName:String,
    val kikobaName:String,
    val kikobaAdmin:Int,
    val notificationID:Int
)
