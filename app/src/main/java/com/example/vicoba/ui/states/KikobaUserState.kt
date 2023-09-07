package com.example.vicoba.ui.states

import com.example.vicoba.data.models.entities.ActiveKikobaUser
import kotlinx.coroutines.flow.MutableStateFlow

/** An object that maintain the state of the kikoba user when login or make registration */

sealed class UserAccountStatus{
    object Loading : UserAccountStatus()
    data class Active(val user: ActiveKikobaUser):UserAccountStatus()
    data class AuthError(val msg:String):UserAccountStatus()
}

object KikobaUserState{
    private val _userState = MutableStateFlow(UserAccountStatus.Active(ActiveKikobaUser()))
    val userState:MutableStateFlow<UserAccountStatus.Active> = _userState
}
