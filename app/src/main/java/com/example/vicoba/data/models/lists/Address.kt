package com.example.vicoba.data.models.lists

import kotlinx.serialization.Serializable

/** A data class that hold values for each address retrieved from vicoba database*/
@Serializable
data class Address(
    val addressID:Int,
    val region:String,
    val district:String,
    val ward:String
)

/** An addresses list contained a list of available addresses returned from vicoba database*/
var addresses:List<Address> = listOf()
