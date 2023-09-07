package com.example.vicoba.data.models.keys

import kotlinx.serialization.Serializable

@Serializable
data class SecretaryLoanKey(
    val loanID:Int,
    val borrowerUserID: Int,
    val kikobaName: String,
    val date:String,
    val secretaryID:Int,
    val kikobaID:Int
)
