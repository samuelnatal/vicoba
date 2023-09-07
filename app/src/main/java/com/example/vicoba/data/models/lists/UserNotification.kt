package com.example.vicoba.data.models.lists

import kotlinx.serialization.Serializable

/** A container for holding user notification */
@Serializable
data class UserNotification(
    val notificationID:Int,
    val notified:Int,
    val notificationTitle:String,
    val notificationBody:String,
    val notifiedDate:String,
    val kikobaInvolved:Int,
)
