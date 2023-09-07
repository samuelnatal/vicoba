package com.example.vicoba.data.models.lists

import kotlinx.serialization.Serializable

@Serializable
data class Debt(
    val debtID:Int = 0,
    val debtorMemberID:Int = 0,
    val debtorUserID:Int = 0,
    val debtorFirstName:String = "",
    val debtorLastName:String = "",
    val debtAmount:Double = 0.00,
    val debtDesc:String = "",
    val debtStatus:String = "",
    val date:String = "",
)
