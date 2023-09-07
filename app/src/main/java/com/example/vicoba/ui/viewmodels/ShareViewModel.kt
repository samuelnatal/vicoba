package com.example.vicoba.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vicoba.VicobaApplication
import com.example.vicoba.data.models.items.ShareCoupon
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.lists.Share
import com.example.vicoba.data.repositories.ShareRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ShareViewModel(
    private val shareRepository: ShareRepository
): ViewModel() {

    private val _membersShares = mutableStateOf(emptyList<Share>())
    val membersShares = _membersShares

    private val _shareFormState = MutableStateFlow(ShareCoupon())
    val shareFormState: StateFlow<ShareCoupon> = _shareFormState.asStateFlow()

    /** A function to update share amount on a share amount field */
    fun updateShareAmount(shareAmount: String) {
        _shareFormState.update { currentState ->
            currentState.copy(
                shareAmount = shareAmount,
            )
        }
    }

    /** A function to buy share by member of a particular kikoba. */
    fun buyShare(shareCoupon: ShareCoupon) {
        viewModelScope.launch {
            try {
                shareRepository.buyShare(shareCoupon)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function to retrieve all shares of particular kikoba */
    fun getAllShares(kikobaID: KikobaID) {
        viewModelScope.launch {
            try {
                _membersShares.value = shareRepository.getAllShares(kikobaID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

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
        val shareFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VicobaApplication)
                val shareRepository = application.container.shareRepository
                ShareViewModel(
                    shareRepository = shareRepository,
                )
            }
        }
    }
}