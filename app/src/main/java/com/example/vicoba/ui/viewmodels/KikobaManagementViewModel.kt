package com.example.vicoba.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vicoba.VicobaApplication
import com.example.vicoba.data.models.entities.EditedKikobaProfile
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.keys.KikobaIDUserID
import com.example.vicoba.data.models.keys.KikobaLeaderID
import com.example.vicoba.data.models.lists.UserRequestedKikoba
import com.example.vicoba.data.repositories.KikobaMemberRepository
import com.example.vicoba.data.repositories.KikobaRepository
import com.example.vicoba.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

class KikobaManagementViewModel(
    private val userRepository: UserRepository,
    private val kikobaMemberRepository: KikobaMemberRepository,
    private val kikobaRepository: KikobaRepository
) : ViewModel() {

    /** A variable referencing a data class that holds details of the new kikoba to be created*/
    private val _editedKikobaProfile = MutableStateFlow(EditedKikobaProfile())
    var editedKikobaProfile = _editedKikobaProfile.asStateFlow()

    /** A state that maintain a list of users who made a request to join a particular kikoba */
    private val _usersRequestedKikobaList = mutableStateOf(emptyList<UserRequestedKikoba>())
    val usersRequestedKikobaList: State<List<UserRequestedKikoba>> = _usersRequestedKikobaList

    /** A state that maintain a list of users who made a request to join a particular kikoba */
    private val _userRequestedKikobaInfo = MutableStateFlow(UserRequestedKikoba())
    val userRequestedKikobaInfo = _userRequestedKikobaInfo.asStateFlow()


    /** Six update functions below are called by edit kikoba profile screen to updates details
     * provided by kikoba admin to update kikoba profile.
     **/
    fun updateKikobaName(name: String) {
        _editedKikobaProfile.update { currentState ->
            currentState.copy(
                kikobaName = name,
            )
        }
    }

    fun updateKikobaDesc(description: String) {
        _editedKikobaProfile.update { currentState ->
            currentState.copy(
                kikobaDesc = description
            )
        }
    }

    fun updateKikobaLocation(location: String) {
        _editedKikobaProfile.update { currentState ->
            currentState.copy(
                kikobaLocationID = location
            )
        }
    }

    fun updateSharePrice(sharePrice: String) {
        _editedKikobaProfile.update { currentState ->
            currentState.copy(
                sharePrice = sharePrice,
            )
        }
    }

    fun updateMaxShare(maxShare: String) {
        _editedKikobaProfile.update { currentState ->
            currentState.copy(
                maxShare = maxShare
            )
        }
    }

    fun updateShareCircle(shareCircle: String) {
        _editedKikobaProfile.update { currentState ->
            currentState.copy(
                shareCircle = shareCircle
            )
        }
    }

    fun setEditedKikobaID(kikobaID: Int) {
        _editedKikobaProfile.update { currentState ->
            currentState.copy(
                kikobaID = kikobaID
            )
        }
    }


    /** A function that retrieve a list of requests to a particular kikoba made by users */
    fun getUsersKikobaRequests(kikobaID: KikobaID) {
        viewModelScope.launch {
            try {
                _usersRequestedKikobaList.value = userRepository.getUsersRequestedKikobaInfo(kikobaID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            } catch (_: SerializationException) {

            }
        }
    }

    /** A function that retrieve information of the single user who requested kikoba */
    fun getUserRequestedKikobaInfo(kikobaIDUserID: KikobaIDUserID) {
        viewModelScope.launch {
            try {
                _userRequestedKikobaInfo.value = userRepository.getUserRequestedKikobaInfo(kikobaIDUserID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            } catch (_: SerializationException) {

            }
        }
    }

    /** A function that approve a request to join kikoba made by users */
    fun approveKikobaJoinRequest(kikobaIDUserID: KikobaIDUserID):Boolean {
        viewModelScope.launch {
            try {
                kikobaMemberRepository.approveKikobaJoinRequest(kikobaIDUserID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            } catch (_: SerializationException) {

            }
        }
        return true
    }

    /** A function that cancel/reject a request to join kikoba made by users */
    fun cancelKikobaJoinRequest(kikobaIDUserID: KikobaIDUserID):Boolean {
        viewModelScope.launch {
            try {
                kikobaMemberRepository.cancelKikobaJoinRequest(kikobaIDUserID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            } catch (_: SerializationException) {

            }
        }
        return true
    }

    /** A function that send invitation request to user to join kikoba */
    fun inviteUserToJoinKikoba(kikobaIDUserID: KikobaIDUserID):Boolean {
        viewModelScope.launch {
            try {
                kikobaMemberRepository.inviteUserToJoinKikoba(kikobaIDUserID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            } catch (_: SerializationException) {

            }
        }
        return true
    }

    /** A function to remove a member from a particular kikoba */
    fun removeKikobaMember(kikobaIDUserID: KikobaIDUserID){
        viewModelScope.launch {
            try {
                kikobaMemberRepository.removeKikobaMember(kikobaIDUserID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            } catch (_: SerializationException) {

            }
        }
    }

    /** A function to remove a member from a particular kikoba */
    fun updateKikobaProfile(editedKikobaProfile: EditedKikobaProfile){
        viewModelScope.launch {
            try {
                kikobaRepository.updateKikobaProfile(editedKikobaProfile)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            } catch (_: SerializationException) {

            }
        }
    }

    /** A function to choose new admin of kikoba group */
    fun selectKikobaAdmin(kikobaLeaderID: KikobaLeaderID){
        viewModelScope.launch {
            try {
                kikobaRepository.selectKikobaAdmin(kikobaLeaderID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            } catch (_: SerializationException) {

            }
        }
    }

    /** A function to choose new secretary of kikoba group */
    fun selectKikobaSecretary(kikobaLeaderID: KikobaLeaderID){
        viewModelScope.launch {
            try {
                kikobaRepository.selectKikobaSecretary(kikobaLeaderID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            } catch (_: SerializationException) {

            }
        }
    }

    /** A function to choose new accountant of kikoba group */
    fun selectKikobaAccountant(kikobaLeaderID: KikobaLeaderID){
        viewModelScope.launch {
            try {
                kikobaRepository.selectKikobaAccountant(kikobaLeaderID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            } catch (_: SerializationException) {

            }
        }
    }

    /** A function to choose new chair person of kikoba group */
    fun selectKikobaChairPerson(kikobaLeaderID: KikobaLeaderID){
        viewModelScope.launch {
            try {
                kikobaRepository.selectKikobaChairPerson(kikobaLeaderID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            } catch (_: SerializationException) {

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
        val kikobaManagementFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VicobaApplication)
                val userRepository = application.container.userRepository
                val kikobaMemberRepository = application.container.kikobaMemberRepository
                val kikobaRepository = application.container.kikobaRepository
                KikobaManagementViewModel(
                    userRepository = userRepository,
                    kikobaMemberRepository = kikobaMemberRepository,
                    kikobaRepository = kikobaRepository
                )
            }
        }
    }
}