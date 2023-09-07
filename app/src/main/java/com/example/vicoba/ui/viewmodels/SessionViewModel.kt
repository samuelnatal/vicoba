package com.example.vicoba.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/** Session controler for the vicoba app user */
class SessionViewModel:ViewModel() {
    /** A varible that maintain the login status of the user if it it true or false */
    private val _userActive = mutableStateOf(false)
    var userActive: State<Boolean> = _userActive


    /** Called when sign in*/
    fun signIn(){
        _userActive.value = true
    }

    /** Called when sign out */
    fun logOut(){
        _userActive.value = false
    }
}