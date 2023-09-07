package com.example.vicoba.data.models.lists

import kotlinx.serialization.Serializable

/** Container for holding user info searched to be added in kikoba. */
@Serializable
data class UserSearchedToAddInKikoba(
    val userID:Int,
    val firstName:String,
    val lastName:String,
)
