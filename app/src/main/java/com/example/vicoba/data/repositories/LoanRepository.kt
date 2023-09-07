package com.example.vicoba.data.repositories

import com.example.vicoba.data.models.items.LoanCoupon
import com.example.vicoba.data.models.keys.AccountantLoanKey
import com.example.vicoba.data.models.keys.ChairPersonLoanKey
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.keys.LoanID
import com.example.vicoba.data.models.keys.MemberID
import com.example.vicoba.data.models.keys.SecretaryLoanKey
import com.example.vicoba.data.models.lists.Loan
import com.example.vicoba.data.models.lists.LoanSummary
import com.example.vicoba.data.network.VicobaApiService

interface LoanRepository {
    suspend fun requestLoan(loanCoupon: LoanCoupon):Boolean
    suspend fun getMyLoans(memberID: MemberID):List<LoanSummary>
    suspend fun getMembersLoans(kikobaID: KikobaID):List<LoanSummary>
    suspend fun getLoanInfo(loanID: LoanID): Loan
    suspend fun rejectLoan(secretaryLoanKey: SecretaryLoanKey): Boolean
    suspend fun acceptLoan(secretaryLoanKey: SecretaryLoanKey): Boolean
    suspend fun approveLoan(chairPersonLoanKey: ChairPersonLoanKey): Boolean
    suspend fun payLoan(accountantLoanKey: AccountantLoanKey): Boolean
}

class DefaultLoanRepository(
    private val vikobaApiService: VicobaApiService
): LoanRepository {
    override suspend fun requestLoan(loanCoupon: LoanCoupon): Boolean {
        return vikobaApiService.requestLoan(loanCoupon)
    }

    override suspend fun getMyLoans(memberID: MemberID): List<LoanSummary> {
        return vikobaApiService.getMyLoans(memberID)
    }

    override suspend fun getMembersLoans(kikobaID: KikobaID): List<LoanSummary> {
        return vikobaApiService.getMembersLoans(kikobaID)
    }

    override suspend fun getLoanInfo(loanID: LoanID): Loan {
        return vikobaApiService.getLoanInfo(loanID)
    }

    override suspend fun rejectLoan(secretaryLoanKey: SecretaryLoanKey): Boolean {
        return vikobaApiService.rejectLoan(secretaryLoanKey)
    }
    override suspend fun acceptLoan(secretaryLoanKey: SecretaryLoanKey): Boolean {
        return vikobaApiService.acceptLoan(secretaryLoanKey)
    }

    override suspend fun approveLoan(chairPersonLoanKey: ChairPersonLoanKey): Boolean {
        return vikobaApiService.approveLoan(chairPersonLoanKey)
    }

    override suspend fun payLoan(accountantLoanKey: AccountantLoanKey): Boolean {
        return vikobaApiService.payLoan(accountantLoanKey)
    }
}