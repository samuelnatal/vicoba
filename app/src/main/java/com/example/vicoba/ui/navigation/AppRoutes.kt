package com.example.vicoba.ui.navigation

import androidx.annotation.StringRes
import com.example.vicoba.R

/** This class contains a list of names that represents destination
 * screens to navigate to within vicoba app
 * */
enum class AppRoutes(@StringRes val title: Int) {
    Splash(title = R.string.splash),
    Login(title = R.string.login),
    Register(title = R.string.register),
    Home(title = R.string.home),
    Dashboard(title = R.string.dashboard),
    CreateKicoba(title = R.string.create_kicoba),
    Search(title = R.string.search_text),
    Notification(title = R.string.notification_text),
    Account(title = R.string.account_text),
    ManageMembers(title = R.string.manage_members),
    InvitedKikoba(title = R.string.invited_kikoba),
    KikobaMembers(title = R.string.kikoba_members),
    KikobaProfile(title = R.string.kikoba_profile),
    KikobaLeaders(title = R.string.kikoba_leaders),
    ManageLeaders(title = R.string.manage_leaders),
    Leaders(title = R.string.leaders),
    RequestLoan(title = R.string.loan_form),
    MyLoans(title = R.string.my_loans),
    ManageLoans(title = R.string.manage_loans),
    MembersLoans(title = R.string.members_loans),
    LoanInfo(title = R.string.loan_info),
    ApproveLoans(title = R.string.approve_loan),
    LoansTransaction(title = R.string.transact_loan),
    BuyShare(title = R.string.buy_share),
    MembersShares(title = R.string.members_share),
    MyShares(title = R.string.my_shares),
    AboutKikoba(title = R.string.about_kikoba),
    KikobaChat(title = R.string.kikoba_chat),
    MyDebts(title = R.string.my_debts),
    MembersDebts(title = R.string.all_debts),
}