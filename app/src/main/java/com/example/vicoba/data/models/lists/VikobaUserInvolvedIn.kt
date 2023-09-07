package com.example.vicoba.data.models.lists

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VikobaUserInvolvedIn(
    val kikobaID:Int,
    val kikobaName:String,
    @SerialName(value = "region")
    val kikobaRegion:String ,
    @SerialName(value = "district")
    val kikobaDistrict:String,
    @SerialName(value = "ward")
    val kikobaWard:String
)

/** A list of retrieved available occupations from vicoba database*/
var vikobaUserInvolved:List<VikobaUserInvolvedIn> = listOf()
