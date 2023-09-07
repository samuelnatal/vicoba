package com.example.vicoba.data.models.lists

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** A container that hold information of a user who sent a request to join a particular kikoba*/
@Serializable
data class UserRequestedKikoba(
    val userKey:Int = 0,
    val kikobaKey:Int = 0,
    val firstName:String = "",
    val lastName:String = "",
    val gender:String = "",
    @SerialName(value = "dob")
    val age:String = "",
    val phone:String = "",
    val email:String = "",
    val region:String = "",
    val district:String = "",
    val ward:String = ""
)
