package com.example.vicoba.data.repositories

import com.example.vicoba.data.models.keys.NotificationID
import com.example.vicoba.data.models.keys.UserID
import com.example.vicoba.data.models.lists.UserNotification
import com.example.vicoba.data.network.VicobaApiService

/** A repository for handling notification data required by vicoba app */
interface NotificationRepository {
    suspend fun getUserNotifications(userID: UserID):List<UserNotification>
    suspend fun deleteViewedNotification(notificationID: NotificationID):Boolean
}

class DefaultNotificationRepository(
    private val vicobaApiService: VicobaApiService
) : NotificationRepository {
    override suspend fun getUserNotifications(userID: UserID): List<UserNotification> {
        return vicobaApiService.getUserNotifications(userID)
    }

    override suspend fun deleteViewedNotification(notificationID: NotificationID): Boolean {
        return vicobaApiService.deleteViewedNotification(notificationID)
    }
}