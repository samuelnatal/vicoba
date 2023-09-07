package com.example.vicoba.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vicoba.VicobaApplication
import com.example.vicoba.data.models.entities.UserLoginCredentials
import com.example.vicoba.data.models.keys.UserIDAddressID
import com.example.vicoba.data.models.keys.UserID
import com.example.vicoba.data.models.lists.vikobaNearUser
import com.example.vicoba.data.models.lists.vikobaUserInvolved
import com.example.vicoba.data.repositories.KikobaRepository
import com.example.vicoba.data.repositories.UserRepository
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.states.UserAccountStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(
    private val userRepository: UserRepository,
    private val kikobaRepository: KikobaRepository
) : ViewModel() {
    /** A variable referencing a data class that holds email and password login credentials */
    private val _userToLogin = MutableStateFlow(UserLoginCredentials())
    var userToLogin = _userToLogin.asStateFlow()

    /** Prepare that make authenticated state observable to other screen*/
    private val _authenticated = MutableStateFlow(false)
    var authenticated = _authenticated.asStateFlow()

    /** A function that reset authentication to faulse when logout */
    fun resetAuth(){
        _authenticated.value = false
    }

    /** A varible refferencing KikobaUserState to maintain the state of the active user*/
    private val _userUiState = KikobaUserState.userState


    /** Two functions below[updateEmail & updatePwd] are called by login screen UI to update the current credentials*/
    fun updateEmail(email: String) {
        _userToLogin.update { currentState ->
            currentState.copy(
                email = email
            )
        }
    }

    fun updatePwd(pwd: String) {
        _userToLogin.update { currentState ->
            currentState.copy(
                pwd = pwd
            )
        }
    }

    /** signIn function receives data class containing login credentials
     * and make network request to login the user through retrofit
     */
    fun authenticateUser(credentials: UserLoginCredentials) {
        viewModelScope.launch {
            try {
                /** Authenticate user from on the internet and return information of the active user */
                _userUiState.value = UserAccountStatus.Active(userRepository.loginUser(credentials))

                /***/
                _authenticated.value = true

                /** Get the key of the logged in user */
                val userID = UserID(
                    userID = _userUiState.value.user.userID
                )

                /** Retrieve vicoba that current user is involved in */
                vikobaUserInvolved = kikobaRepository.getVikobaUserInvolvedIn(userID)


                /** Set userAddress key needed for retrieving vikoba located near user*/
                val userIDAddressID = UserIDAddressID(
                    userID = _userUiState.value.user.userID,
                    userAddressID = _userUiState.value.user.addressKey
                )

                /** Retrieve vicoba that are located near the active user */
                vikobaNearUser = kikobaRepository.getVikobaNearUser(userIDAddressID)

            } catch (e: SerializationException) {
                _authenticated.value = false
            } catch (e: IOException) {
                _authenticated.value = false

            } catch (e: HttpException) {
                _authenticated.value = false
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
        val loginFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VicobaApplication)
                val userRepository = application.container.userRepository
                val kikobaRepository = application.container.kikobaRepository
                LoginViewModel(
                    userRepository = userRepository, kikobaRepository = kikobaRepository
                )
            }
        }
    }
}