package com.example.vicoba.data.models.items

import kotlinx.serialization.Serializable

@Serializable
data class ShareCoupon(
    val userID:Int = 0,
    val memberID:Int = 0,
    val kikobaID:Int = 0,
    val kikobaName:String = "",
    val updatedWallet:Double = 0.00,
    val shareAmount:String = ""
)
