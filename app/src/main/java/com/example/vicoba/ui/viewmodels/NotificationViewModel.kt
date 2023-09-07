package com.example.vicoba.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vicoba.VicobaApplication
import com.example.vicoba.data.models.keys.NotificationID
import com.example.vicoba.data.models.keys.UserID
import com.example.vicoba.data.models.lists.UserNotification
import com.example.vicoba.data.repositories.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

class NotificationViewModel(
    private val notificationRepository: NotificationRepository
):ViewModel() {

    /** A state tha observes a list of notifications retrieved from the internet vicoba app database*/
    private val _notifications = mutableStateOf(emptyList<UserNotification>())
    var notifications: State<List<UserNotification>> = _notifications

    private val _currentNotificationID = MutableStateFlow(0)
    var currentNotificationID = _currentNotificationID.asStateFlow()

    /** A function to set current notification ID required in several notification operation
     */
    fun setNotificationID(notificationID: Int) {
        _currentNotificationID.value = notificationID
    }

    /** A function that retrive a list of requests to a particular kikoba made by users */
    fun getUserNotifications(userID: UserID) {
        viewModelScope.launch {
            try {
                _notifications.value = notificationRepository.getUserNotifications(userID)
            } catch (e: IOException) {
                Log.e("getReq", "Error: ", e)
            } catch (e: HttpException) {
                Log.e("getReq", "Error: ", e)
            } catch (e: SerializationException) {
                Log.e("getReq", "Error: ", e)
            }
        }
    }

    /** A function to delete viewed notification */
    fun deleteViewedNotification(notificationID: NotificationID) {
        viewModelScope.launch {
            try {
                notificationRepository.deleteViewedNotification(notificationID)
            } catch (e: IOException) {
                Log.e("getReq", "Error: ", e)
            } catch (e: HttpException) {
                Log.e("getReq", "Error: ", e)
            } catch (e: SerializationException) {
                Log.e("getReq", "Error: ", e)
            }
        }
    }

    /**
     * Because the Android framework does not allow a ViewModel
     * to be passed values in the constructor when created,
     * we implement a ViewModelProvider.Factory object,
     * which lets us get around this limitation.
     */
    companion object {
        val notificationFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VicobaApplication)
                val notificationRepository = application.container.notificationRepository
                NotificationViewModel(
                    notificationRepository = notificationRepository,
                )
            }
        }
    }
}