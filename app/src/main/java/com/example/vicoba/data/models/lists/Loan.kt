package com.example.vicoba.data.models.lists

import kotlinx.serialization.Serializable

@Serializable
data class Loan(
    val loanID:Int = 0,
    val borrowerUserID:Int = 0,
    val borrowerMemberID:Int = 0,
    val borrowerFirstName:String = "",
    val borrowerLastName:String = "",
    val loanAmount:Double = 0.00,
    val loanDesc:String = "",
    val guarantee:String = "",
    val requestedAt:String = "",
    val status:String = "",
    val statusMessage:String = "",
    val guarantor1FirstName:String = "",
    val guarantor1LastName:String = "",
    val guarantor2FirstName:String = "",
    val guarantor2LastName:String = "",
    val guarantor3FirstName:String = "",
    val guarantor3LastName:String = "",
)
