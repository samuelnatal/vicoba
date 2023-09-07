package com.example.vicoba.data.models.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** A data class that holds information of the logged in or registered user*/
@Serializable
data class ActiveKikobaUser(
    val userID:Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val gender: String = "",
    val date: String = "",
    val phone: String = "",
    val addressKey:Int = 0,
    val region: String = "",
    val district: String = "",
    val ward: String = "",
    @SerialName(value = "occupation_name")
    val occupationName: String = "",
)

