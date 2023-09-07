package com.example.vicoba.data.models.keys

import kotlinx.serialization.Serializable

@Serializable
data class KikobaLeaderID(
    val kikobaKey:Int,
    val memberKey:Int,
    val userID: Int,
    val kikobaName:String
)
