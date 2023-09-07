package com.example.vicoba.data.models.lists

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** A data class that hold occupation object containing occupationID
 * and occupation name retrieved from tne internet vicoba database
 */
@Serializable
data class Occupation(
    val occupationID:Int,
    @SerialName(value = "occupation_name")
    val occupationName:String
)

/** A list of retrieved available occupations from vicoba database*/
var occupations:List<Occupation> = listOf()
