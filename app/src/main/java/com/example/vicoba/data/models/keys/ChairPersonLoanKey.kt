package com.example.vicoba.data.models.keys

import kotlinx.serialization.Serializable

@Serializable
data class ChairPersonLoanKey(
    val loanID:Int,
    val borrowerUserID: Int,
    val kikobaName: String,
    val date:String,
    val chairPersonID:Int,
    val kikobaID:Int
)
