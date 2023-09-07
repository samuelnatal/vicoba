package com.example.vicoba.data.models.keys

import kotlinx.serialization.Serializable

@Serializable
data class AccountantLoanKey(
    val loanID:Int,
    val borrowerUserID: Int,
    val borrowerMemberID: Int,
    val kikobaName: String,
    val date:String,
    val accountantID:Int,
    val kikobaID:Int,
    val updatedWallet:Double,
    val debtAmount:Double
)

