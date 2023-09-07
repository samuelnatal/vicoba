package com.example.vicoba.data.models.lists

import kotlinx.serialization.Serializable

@Serializable
data class LoanSummary(
    val loanID:Int,
    val borrowerUserID:Int,
    val borrowerMemberID:Int,
    val borrowerFirstName:String,
    val borrowerLastName:String,
    val loanAmount:Double,
    val requestedAt:String,
    val status:String,
    val statusMessage:String
)
