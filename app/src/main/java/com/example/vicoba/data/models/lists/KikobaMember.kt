package com.example.vicoba.data.models.lists

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KikobaMember(
    @SerialName(value = "userKey")
    val userID:Int = 0,
    val memberID:Int = 0,
    val firstName:String = "",
    val lastName:String = "",
)
