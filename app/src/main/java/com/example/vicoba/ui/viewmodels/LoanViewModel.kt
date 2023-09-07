package com.example.vicoba.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vicoba.VicobaApplication
import com.example.vicoba.data.models.items.LoanCoupon
import com.example.vicoba.data.models.keys.AccountantLoanKey
import com.example.vicoba.data.models.keys.ChairPersonLoanKey
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.keys.LoanID
import com.example.vicoba.data.models.keys.MemberID
import com.example.vicoba.data.models.keys.SecretaryLoanKey
import com.example.vicoba.data.models.lists.Loan
import com.example.vicoba.data.models.lists.LoanSummary
import com.example.vicoba.data.repositories.LoanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoanViewModel(
    private val loanRepository: LoanRepository
): ViewModel() {

    private val _loanFormState = MutableStateFlow(LoanCoupon())
    val loanFormState: StateFlow<LoanCoupon> = _loanFormState.asStateFlow()

    private val _Loans = mutableStateOf(emptyList<LoanSummary>())
    val myloans = _Loans

    private val _loanInfo = mutableStateOf(Loan())
    val loanInfo = _loanInfo

    private val _membersLoans = mutableStateOf(emptyList<LoanSummary>())
    val membersLoans = _membersLoans

    /** A function to set memberKey of the loan coupon for loan request */
    fun setKikobaMemberID(memberID: Int) {
        _loanFormState.update { currentState ->
            currentState.copy(
                borrowerID = memberID,
            )
        }
    }

    /** A function to set kikobaKey of the loan coupon for loan request */
    fun setKikobaKey(kikobaID: Int) {
        _loanFormState.update { currentState ->
            currentState.copy(
                kikobaKey = kikobaID,
            )
        }
    }

    /**Below is the list of update callback functions required by the loan form screen
     * data fields to update and display data provided by the user instantly*/
    fun updateLoanAmount(loanAmount: String) {
        _loanFormState.update { currentState ->
            currentState.copy(
                loanAmount = loanAmount,
            )
        }
    }

    fun updateLoanDesc(loanDesc: String) {
        _loanFormState.update { currentState ->
            currentState.copy(
                loanDesc = loanDesc,
            )
        }
    }

    fun updateGuarantee(guarantee: String) {
        _loanFormState.update { currentState ->
            currentState.copy(
                guarantee = guarantee,
            )
        }
    }

    fun updateGuarantor1ID(guarantor1ID: String) {
        _loanFormState.update { currentState ->
            currentState.copy(
                guarantor1ID = guarantor1ID,
            )
        }
    }

    fun updateGuarantor2ID(guarantor2ID: String) {
        _loanFormState.update { currentState ->
            currentState.copy(
                guarantor2ID = guarantor2ID,
            )
        }
    }

    fun updateGuarantor3ID(guarantor3ID: String) {
        _loanFormState.update { currentState ->
            currentState.copy(
                guarantor3ID = guarantor3ID,
            )
        }
    }

    /** A function for a member of kikoba to request a loan */
    fun requestLoan(loanCoupon: LoanCoupon) {
        viewModelScope.launch {
            try {
                loanRepository.requestLoan(loanCoupon)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function to retrieve loans of a particular member */
    fun getMyLoans(memberID: MemberID) {
        viewModelScope.launch {
            try {
                _Loans.value = loanRepository.getMyLoans(memberID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function to retrieve loans of a particular member */
    fun getMembersLoans(kikobaID: KikobaID) {
        viewModelScope.launch {
            try {
                _membersLoans.value = loanRepository.getMembersLoans(kikobaID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function to retrieve full details about a particular loan */
    fun getLoanInfo(loanID: LoanID) {
        viewModelScope.launch {
            try {
                _loanInfo.value = loanRepository.getLoanInfo(loanID)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function to reject loan request by a secretary */
    fun rejectLoan(secretaryLoanKey: SecretaryLoanKey) {
        viewModelScope.launch {
            try {
                loanRepository.rejectLoan(secretaryLoanKey)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function to reject loan request by a secretary */
    fun acceptLoan(secretaryLoanKey: SecretaryLoanKey) {
        viewModelScope.launch {
            try {
                loanRepository.acceptLoan(secretaryLoanKey)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function to reject loan request by a secretary */
    fun approveLoan(chairPersonLoanKey: ChairPersonLoanKey) {
        viewModelScope.launch {
            try {
                loanRepository.approveLoan(chairPersonLoanKey)
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }

    /** A function to reject loan request by a secretary */
    fun payLoan(accountantLoanKey: AccountantLoanKey) {
        viewModelScope.launch {
            try {
                loanRepository.payLoan(accountantLoanKey)
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
        val loanFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VicobaApplication)
                val loanRepository = application.container.loanRepository
                LoanViewModel(
                    loanRepository = loanRepository,
                )
            }
        }
    }
}