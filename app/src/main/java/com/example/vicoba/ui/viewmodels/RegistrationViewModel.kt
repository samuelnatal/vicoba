package com.example.vicoba.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vicoba.VicobaApplication
import com.example.vicoba.data.models.entities.NewKikobaUser
import com.example.vicoba.data.models.keys.UserIDAddressID
import com.example.vicoba.data.models.keys.UserID
import com.example.vicoba.data.models.lists.addresses
import com.example.vicoba.data.models.lists.occupations
import com.example.vicoba.data.models.lists.vikobaNearUser
import com.example.vicoba.data.models.lists.vikobaUserInvolved
import com.example.vicoba.data.repositories.AddressRepository
import com.example.vicoba.data.repositories.KikobaRepository
import com.example.vicoba.data.repositories.OccupationRepository
import com.example.vicoba.data.repositories.UserRepository
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.states.UserAccountStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

/**Registration view model communicate with the registration screen UI
 * to provide and update the required data */
class RegistrationViewModel(
    private val userRepository: UserRepository,
    private val occupationRepository: OccupationRepository,
    private val addressRepository: AddressRepository,
    private val kikobaRepository: KikobaRepository
) : ViewModel() {

    /**
     * Creating a _newKikobaUserState that observes the state of the new user data information,
     * from the NewKikobaUser data class,
     * and backing it to avoid state updates from other classes
     */
    private val _newKikobaUserState = MutableStateFlow(NewKikobaUser())
    val newKikobaUserState: StateFlow<NewKikobaUser> = _newKikobaUserState.asStateFlow()

    /**
     * _userUiState holds information of the active user provided by the ActiveKikobaUser data class
     */
    private val _userUiState = KikobaUserState.userState
    var userUiState: MutableStateFlow<UserAccountStatus.Active> = _userUiState


    /**Below is the list of update callback functions required by the registration
     * form data field to updates and display data provided by the user instantly*/
    fun updateFirstName(fname: String) {
        _newKikobaUserState.update { currentState ->
            currentState.copy(
                firstName = fname,
            )
        }
    }

    fun updateLastName(lname: String) {
        _newKikobaUserState.update { currentState ->
            currentState.copy(
                lastName = lname,
            )
        }
    }

    fun updateEmail(email: String) {
        _newKikobaUserState.update { currentState ->
            currentState.copy(
                email = email,
            )
        }
    }

    fun updateAddress(address: String) {
        _newKikobaUserState.update { currentState ->
            currentState.copy(
                address = address,
            )
        }
    }

    fun updateGender(gender: String) {
        _newKikobaUserState.update { currentState ->
            currentState.copy(
                gender = gender,
            )
        }
    }

    fun updateOccupation(occupation: String) {
        _newKikobaUserState.update { currentState ->
            currentState.copy(
                occupation = occupation,
            )
        }
    }

    fun updatePhone(phone: String) {
        _newKikobaUserState.update { currentState ->
            currentState.copy(
                phone = phone,
            )
        }
    }

    fun updatePwd(pwd: String) {
        _newKikobaUserState.update { currentState ->
            currentState.copy(
                pwd = pwd,
            )
        }
    }

    fun updateDob(date: String) {
        _newKikobaUserState.update { currentState ->
            currentState.copy(
                dob = date,
            )
        }
    }

    /** Init function for the contents that needs to be updated earlier once the app launches*/
    init {
        getOccupations()
        getAddresses()
    }

    /** A function fetch occupation list from the occupation repository */
    private fun getOccupations() {
        viewModelScope.launch {
            try {
                occupations = occupationRepository.getOccupations()
            } catch (e: IOException) {
                Log.i("g", "$userUiState")
            } catch (e: HttpException) {
                Log.i("g", "$userUiState")
            }
        }
    }

    /** A function fetch occupation list from the occupation repository */
    private fun getAddresses() {
        viewModelScope.launch {
            try {
                addresses = addressRepository.getAddresses()
            } catch (e: IOException) {
                Log.i("g", "$userUiState")
            } catch (e: HttpException) {
                Log.i("g", "$userUiState")
            }
        }
    }


    /** A function that send a request to the vicoba database API to store new user information */
    fun registerNewUser(newUser: NewKikobaUser) {
        viewModelScope.launch {
            try {
                _userUiState.value = UserAccountStatus.Active(userRepository.registerUser(newUser))

                val userID = UserID(
                    userID = _userUiState.value.user.userID
                )
                vikobaUserInvolved = kikobaRepository.getVikobaUserInvolvedIn(userID)

                val userIDAddressID = UserIDAddressID(
                    userID = _userUiState.value.user.userID,
                    userAddressID = _userUiState.value.user.addressKey
                )
                vikobaNearUser = kikobaRepository.getVikobaNearUser(userIDAddressID)


            }catch (_: SerializationException) {

            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }

        Log.i("invfromUI", "$vikobaUserInvolved")
    }

    /**
     * Because the Android framework does not allow a ViewModel
     * to be passed values in the constructor when created,
     * we implement a ViewModelProvider.Factory object,
     * which lets us get around this limitation.
     */
    companion object {
        val registrationFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                /**
                 * The APPLICATION_KEY is part of the ViewModelProvider.AndroidViewModelFactory.Companion object
                 * and is used to find the app's MarsPhotosApplication object,
                 * which has the container property used to retrieve the repository used for dependency injection
                 */
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VicobaApplication)
                val userRepository = application.container.userRepository
                val occupationRepository = application.container.occupationRepository
                val addressRepository = application.container.addressRepository
                val kikobaRepository = application.container.kikobaRepository
                RegistrationViewModel(
                    userRepository = userRepository,
                    occupationRepository = occupationRepository,
                    addressRepository = addressRepository,
                    kikobaRepository = kikobaRepository
                )
            }
        }
    }
}
