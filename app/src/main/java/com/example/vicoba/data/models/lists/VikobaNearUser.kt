package com.example.vicoba.data.models.lists

import kotlinx.serialization.Serializable

/** A data class representing representing information of a single kikoba near user */
@Serializable
data class VikobaNearUser(
    val kikobaID:Int,
    val kikobaName:String,
    val status:String,
)

/** A list of retrieved available occupations from vicoba database */
var vikobaNearUser:List<VikobaNearUser> = listOf()
