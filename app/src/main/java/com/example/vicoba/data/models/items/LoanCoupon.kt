package com.example.vicoba.data.models.items

import kotlinx.serialization.Serializable

@Serializable
data class LoanCoupon(
    val borrowerID:Int = 0,
    val loanAmount:String = "",
    val loanDesc:String = "",
    val guarantee:String = "",
    val guarantor1ID:String = "",
    val guarantor2ID:String = "",
    val guarantor3ID:String = "",
    val kikobaKey:Int = 0,
)
