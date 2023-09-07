package com.example.vicoba.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vicoba.VicobaApplication
import com.example.vicoba.data.models.entities.ActiveKikoba
import com.example.vicoba.data.models.entities.InvitedKikoba
import com.example.vicoba.data.models.entities.KikobaRequestCredentials
import com.example.vicoba.data.models.entities.NewKikoba
import com.example.vicoba.data.models.items.InvitationCoupon
import com.example.vicoba.data.models.items.TotalKikobaMembers
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.keys.KikobaIDUserID
import com.example.vicoba.data.models.lists.KikobaMember
import com.example.vicoba.data.repositories.KikobaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class KikobaViewModel(private val kikobaRepository: KikobaRepository) : ViewModel() {

    /** A variable referencing a data class that holds details of the new kikoba to be created*/
    private val _kikobaToAdd = MutableStateFlow(NewKikoba())
    var kikobaToAdd = _kikobaToAdd.asStateFlow()

    /** A variable referencing a data class that holds details of approved kikoba */
    private val _activeKikoba = MutableStateFlow(ActiveKikoba())
    var activeKikoba = _activeKikoba.asStateFlow()

    /** A variable referencing for holding data of the kikoba invited */
    private val _invitedKikoba = MutableStateFlow(InvitedKikoba())
    var invitedKikoba = _invitedKikoba.asStateFlow()

    /** A variable referencing for holding data of the kikoba invited */
    private val _kikobaMemberID = MutableStateFlow(0)
    var kikobaMemberID = _kikobaMemberID.asStateFlow()

    /** A variable for maintaining join kikoba request button status  at the home screen */
    private val _requestStatus = MutableStateFlow(false)
    var requestStatus = _requestStatus.asStateFlow()

    /** A state for maintaining kikoba invitation accept button status at the home screen */
    private val _invitationAcceptationStatus = MutableStateFlow(false)
    var invitationAcceptationStatus = _invitationAcceptationStatus.asStateFlow()

    /** A state for maintaining kikoba invitation reject button status at the home screen */
    private val _invitationRejectionStatus = MutableStateFlow(false)
    var invitationRejectionStatus = _invitationRejectionStatus.asStateFlow()

    /** A state for maintaining a list of kikoba members */
    private val _members = mutableStateOf(emptyList<KikobaMember>())
    var members = _members

    /** A state for maintaining total number of available members in a particular kikoba */
    private val _totalMembers = mutableStateOf(TotalKikobaMembers())
    var totalMembers = _totalMembers


    /** A function that set up the ID of the user who own kikoba */
    fun setKikobaOwner(userID: Int) {
        _kikobaToAdd.update { currentState ->
            currentState.copy(
                kikobaOwnerID = userID
            )
        }
    }

    /** Three update functions below are called by createKikobaScreen to updates details
     * provided by a user for the new kikoba
     **/
    fun updateKikobaName(name: String) {
        _kikobaToAdd.update { currentState ->
            currentState.copy(
                kikobaName = name,
            )
        }
    }

    fun updateKikobaDesc(description: String) {
        _kikobaToAdd.update { currentState ->
            currentState.copy(
                kikobaDesc = description
            )
        }
    }

    fun updateKikobaLocation(location: String) {
        _kikobaToAdd.update { currentState ->
            currentState.copy(
                kikobaLocationID = location
            )
        }
    }

    /** A function that communicate with kikoba repository to save new kikoba information's */
    fun saveNewKikoba(newKikoba: NewKikoba) {
        viewModelScope.launch {
            try {
                _activeKikoba.value = kikobaRepository.saveNewKikoba(newKikoba)
            } catch (e: IOException) {
                Log.e("saveKikoba", "Error: ", e)
            } catch (e: HttpException) {
                Log.e("saveKikoba", "Error: ", e)
            }
        }
    }

    /** A function that communicate with kikoba repository to get information of the particular kikoba */
    fun getKikobaInfo(kikobaID: KikobaID) {
        viewModelScope.launch {
            try {
                _activeKikoba.value = kikobaRepository.getKikobaInfo(kikobaID)
            } catch (e: IOException) {
                Log.e("saveKikoba", "Error: ", e)
            } catch (e: HttpException) {
                Log.e("saveKikoba", "Error: ", e)
            }
        }
    }

    /** A function that get details of the invited kikoba from the kikoba repository */
    fun getInvitedKikobaInfo(kikobaID: KikobaID) {
        viewModelScope.launch {
            try {
                _invitedKikoba.value = kikobaRepository.getInvitedKikobaInfo(kikobaID)
            } catch (e: IOException) {
                Log.e("saveKikoba", "Error: ", e)
            } catch (e: HttpException) {
                Log.e("saveKikoba", "Error: ", e)
            }
        }
    }

    /** A function that communicate with kikoba repository to save a request for a user to join kikoba */
    fun saveKikobaJoinRequest(kikobaRequestCredentials: KikobaRequestCredentials) {
        viewModelScope.launch {
            try {
                kikobaRepository.saveKikobaJoinRequest(kikobaRequestCredentials)
                _requestStatus.value = true
            } catch (e: IOException) {
                _requestStatus.value = false
            } catch (e: HttpException) {
                _requestStatus.value = false
            }
        }
    }

    /** A function to retrieve a list of members from a particular kikoba */
    fun getKikobaMembers(kikobaID: KikobaID) {
        viewModelScope.launch {
            try {
                _members.value = kikobaRepository.getKikobaMembers(kikobaID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function to retrieve a list of members from a particular kikoba */
    fun getTotalKikobaMembers(kikobaID: KikobaID) {
        viewModelScope.launch {
            try {
                _totalMembers.value = kikobaRepository.getTotalKikobaMembers(kikobaID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function for handling kikoba invitation acceptation by a user. */
    fun acceptKikobaInvitation(invitationCoupon: InvitationCoupon) {
        viewModelScope.launch {
            try {
                _invitationAcceptationStatus.value = kikobaRepository.acceptKikobaInvitation(invitationCoupon)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function for handling kikoba invitation rejection by a user. */
    fun rejectKikobaInvitation(invitationCoupon: InvitationCoupon) {
        viewModelScope.launch {
            try {
                _invitationRejectionStatus.value = kikobaRepository.rejectKikobaInvitation(invitationCoupon)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function for retrieving kikoba profile information [Name,Description/Rules,Location]. */
    fun getKikobaMemberID(kikobaIDUserID: KikobaIDUserID) {
        viewModelScope.launch {
            try {
                _kikobaMemberID.value = kikobaRepository.getKikobaMemberID(kikobaIDUserID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }
    
    companion object {
        val kikobaFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                /**
                 * The APPLICATION_KEY is part of the ViewModelProvider.AndroidViewModelFactory.Companion object
                 * and is used to find the app's MarsPhotosApplication object,
                 * which has the container property used to retrieve the repository used for dependency injection
                 */
                /**
                 * The APPLICATION_KEY is part of the ViewModelProvider.AndroidViewModelFactory.Companion object
                 * and is used to find the app's MarsPhotosApplication object,
                 * which has the container property used to retrieve the repository used for dependency injection
                 */
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VicobaApplication)
                val kikobaRepository = application.container.kikobaRepository
                KikobaViewModel(
                    kikobaRepository = kikobaRepository,
                )
            }
        }
    }
}