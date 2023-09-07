package com.example.vicoba.data.models.entities

import kotlinx.serialization.Serializable

/** A data class that holds information of a new user */
@Serializable
data class NewKikobaUser(
    val firstName:String = "",
    val lastName:String = "",
    val email:String = "",
    val gender:String = "",
    val dob:String = "",
    val pwd:String = "",
    val phone:String = "",
    val address:String = "",
    val occupation:String = ""
)
