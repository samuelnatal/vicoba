package com.example.vicoba.utility

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/** A function to format a date string retrieved from vicoba database */
fun formatDate(timestampString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val dateTime = LocalDateTime.parse(timestampString, formatter)
    val currentDateTime = LocalDateTime.now(ZoneOffset.UTC)

    val duration = Duration.between(dateTime, currentDateTime)
    val minutes = duration.toMinutes()
    val hours = duration.toHours()
    val days = duration.toDays()

    return when {
        minutes < 1 -> "Few sec ago"
        minutes < 60 -> "$minutes min ago"
        hours < 24 -> "$hours hours ago"
        else -> "$days days ago"
    }
}
