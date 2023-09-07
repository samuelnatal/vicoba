package com.example.vicoba.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vicoba.VicobaApplication
import com.example.vicoba.data.models.items.DebtCoupon
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.lists.Debt
import com.example.vicoba.data.repositories.DebtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DebtViewModel(
    private val debtRepository: DebtRepository
) : ViewModel() {

    private val _membersDebts = mutableStateOf(emptyList<Debt>())
    val membersDebts = _membersDebts

    private val _debtFormState = MutableStateFlow(DebtCoupon())
    val debtFormState: StateFlow<DebtCoupon> = _debtFormState.asStateFlow()


    /** A function to pay a debt by member of a particular kikoba. */
    fun payDebt(debtCoupon: DebtCoupon) {
        viewModelScope.launch {
            try {
                debtRepository.payDebt(debtCoupon)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function to retrieve all debts of particular kikoba */
    fun getAllDebts(kikobaID: KikobaID) {
        viewModelScope.launch {
            try {
               _membersDebts.value = debtRepository.getAllDebts(kikobaID)
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
        val debtFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VicobaApplication)
                val debtRepository = application.container.debtRepository
                DebtViewModel(
                    debtRepository = debtRepository,
                )
            }
        }
    }
}