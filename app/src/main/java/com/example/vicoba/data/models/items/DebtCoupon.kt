package com.example.vicoba.data.models.items

import kotlinx.serialization.Serializable

@Serializable
data class DebtCoupon(
    val userID:Int = 0,
    val debtID:Int = 0,
    val kikobaID:Int = 0,
    val kikobaName:String = "",
    val updatedWallet:Double = 0.00,
    val debtAmount:Double = 0.00
)
