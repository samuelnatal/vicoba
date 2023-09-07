package com.example.vicoba.data.models.entities

import kotlinx.serialization.Serializable

/** A data class that contains credentials needed for login process*/
@Serializable
data class UserLoginCredentials(
    val email:String = "",
    val pwd:String = ""
)
