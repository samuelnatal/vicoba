package com.example.vicoba.data.models.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** The data class that holds the information of the current active kikoba */
@Serializable
data class ActiveKikoba(
    val kikobaID: Int = 0,
    val kikobaName: String = "",
    val kikobaDesc: String = "",
    val kikobaWallet: Double = 0.00,
    val kikobaOwnerID: Int = 0,
    val createdAt: String = "",
    val sharePrice: Double = 0.00,
    val maxShare: Int = 0,
    val shareCircle: String = "",
    val kikobaAdmin: Int = 0,
    val kikobaSecretaryID: Int = 0,
    val kikobaAccountantID: Int = 0,
    val kikobaChairPersonID: Int = 0,
    val kikobaSecretaryName: String = "",
    val kikobaAccountantName: String = "",
    val kikobaChairPersonName: String = "",
    @SerialName(value = "region")
    val kikobaRegion: String = "",
    @SerialName(value = "district")
    val kikobaDistrict: String = "",
    @SerialName(value = "ward")
    val kikobaWard: String = "",
    @SerialName(value = "firstName")
    val ownerFirstName: String = "",
    @SerialName(value = "lastName")
    val ownerLastName: String = "",
    @SerialName(value = "email")
    val ownerEmail: String = "",
    @SerialName(value = "phone")
    val ownerPhone: String = ""
)
