package com.example.vicoba.ui.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vicoba.R
import com.example.vicoba.ui.screens.accountant.LoansTransactionScreen
import com.example.vicoba.ui.screens.admin.ManageLeadersScreen
import com.example.vicoba.ui.screens.admin.MembersScreen
import com.example.vicoba.ui.screens.app.SplashScreen
import com.example.vicoba.ui.screens.chairPerson.ApproveLoansScreen
import com.example.vicoba.ui.screens.kikoba.AboutKikobaScreen
import com.example.vicoba.ui.screens.kikoba.CreateKicobaScreen
import com.example.vicoba.ui.screens.kikoba.DashboardScreen
import com.example.vicoba.ui.screens.kikoba.KikobaChatScreen
import com.example.vicoba.ui.screens.kikoba.KikobaInvitationScreen
import com.example.vicoba.ui.screens.kikoba.KikobaLeadersScreen
import com.example.vicoba.ui.screens.kikoba.KikobaMembersScreen
import com.example.vicoba.ui.screens.kikoba.KikobaProfileScreen
import com.example.vicoba.ui.screens.member.BuyShareScreen
import com.example.vicoba.ui.screens.member.MembersDebtsScreen
import com.example.vicoba.ui.screens.member.MembersLoansScreen
import com.example.vicoba.ui.screens.member.MembersSharesScreen
import com.example.vicoba.ui.screens.member.MyDebtsScreen
import com.example.vicoba.ui.screens.member.MyLoansScreen
import com.example.vicoba.ui.screens.member.MySharesScreen
import com.example.vicoba.ui.screens.member.RequestLoanScreen
import com.example.vicoba.ui.screens.secretary.LoanInfoScreen
import com.example.vicoba.ui.screens.secretary.ManageLoansScreen
import com.example.vicoba.ui.screens.user.AccountScreen
import com.example.vicoba.ui.screens.user.HomeScreen
import com.example.vicoba.ui.screens.user.LoginScreen
import com.example.vicoba.ui.screens.user.NotificationScreen
import com.example.vicoba.ui.screens.user.RegisterScreen
import com.example.vicoba.ui.screens.user.SearchScreen
import com.example.vicoba.ui.viewmodels.DebtViewModel
import com.example.vicoba.ui.viewmodels.KikobaManagementViewModel
import com.example.vicoba.ui.viewmodels.KikobaViewModel
import com.example.vicoba.ui.viewmodels.LoanViewModel
import com.example.vicoba.ui.viewmodels.LoginViewModel
import com.example.vicoba.ui.viewmodels.NotificationViewModel
import com.example.vicoba.ui.viewmodels.RegistrationViewModel
import com.example.vicoba.ui.viewmodels.SearchViewModel
import com.example.vicoba.ui.viewmodels.SessionViewModel
import com.example.vicoba.ui.viewmodels.ShareViewModel

/**
 * NavGraph composable fun contains NahHost container which
 * holds refferences to all screen composables required by vicoba app
 */
@Composable
fun NavGraph(
    regViewModel: RegistrationViewModel,
    loginViewModel: LoginViewModel,
    kikobaViewModel: KikobaViewModel,
    kikobaManagementViewModel: KikobaManagementViewModel,
    notificationViewModel: NotificationViewModel,
    sessionViewModel: SessionViewModel,
    searchViewModel: SearchViewModel,
    loanViewModel: LoanViewModel,
    shareViewModel: ShareViewModel,
    debtViewModel: DebtViewModel,
    navHostController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = AppRoutes.Splash.name,
        modifier = modifier
    ) {
        /** A navhost for the Splash screen screen*/
        composable(route = AppRoutes.Splash.name) {
            SplashScreen(navHostController)
        }

        /** A navhost for the Login screen*/
        composable(route = AppRoutes.Login.name) {
            LoginScreen(
                navController = navHostController,
                loginViewModel = loginViewModel,
                sessionViewModel = sessionViewModel,
                onSignUpButtonClicked = { navHostController.navigate(AppRoutes.Register.name) },
                modifier = modifier
            )
        }

        /** A navhost for the Registration screen*/
        composable(route = AppRoutes.Register.name) {
            RegisterScreen(
                regViewModel = regViewModel,
                sessionViewModel = sessionViewModel,
                navHostController = navHostController,
                modifier = Modifier.fillMaxHeight()
            )
        }

        /** A navhost for the Dashboard screen */
        composable(route = AppRoutes.Home.name) {
            HomeScreen(
                kikobaViewModel = kikobaViewModel,
                navController = navHostController,
                loanViewModel = loanViewModel,
                shareViewModel = shareViewModel,
                debtViewModel = debtViewModel,
                modifier = Modifier.fillMaxHeight(),
            )
        }

        /** A navhost for the creating kikoba screen */
        composable(route = AppRoutes.CreateKicoba.name) {
            CreateKicobaScreen(
                kikobaViewModel = kikobaViewModel,
                navController = navHostController,
                modifier = Modifier.fillMaxHeight()
            )
        }

        /** A navhost for the kikoba dashboard screen */
        composable(route = AppRoutes.Dashboard.name) {
            DashboardScreen(
                kikobaViewModel = kikobaViewModel,
                kikobaManagementViewModel = kikobaManagementViewModel,
                navHostController = navHostController,
                loanViewModel = loanViewModel
            )
        }

        /** A navhost for the search screen */
        composable(route = AppRoutes.Search.name) {
            SearchScreen(
                searchViewModel = searchViewModel
            )
        }

        /** A navhost for the Notification screen */
        composable(route = AppRoutes.Notification.name) {
            NotificationScreen(
                notificationViewModel = notificationViewModel,
                kikobaViewModel = kikobaViewModel,
                navHostController = navHostController
            )
        }

        /** A navhost for the Profile screen*/
        composable(route = AppRoutes.Account.name) {
            AccountScreen(
                sessionViewModel = sessionViewModel,
                loginViewModel = loginViewModel,
                navController = navHostController
            )
        }

        /** A navhost for the Manage Members screen */
        composable(route = AppRoutes.ManageMembers.name) {
            MembersScreen(
                kikobaManagementViewModel = kikobaManagementViewModel,
                kikobaViewModel = kikobaViewModel,
                searchViewModel = searchViewModel,
            )
        }

        /** A navhost for the invited kikoba information screen */
        composable(route = AppRoutes.InvitedKikoba.name) {
            KikobaInvitationScreen(
                kikobaViewModel = kikobaViewModel,
                notificationViewModel = notificationViewModel
            )
        }

        /** A navhost for the screen that display a list of members of a particular kikoba */
        composable(route = AppRoutes.KikobaMembers.name) {
            KikobaMembersScreen(
                kikobaViewModel = kikobaViewModel,
                modifier = modifier
            )
        }

        /** A navhost for the screen that display a list of members of a particular kikoba */
        composable(route = AppRoutes.KikobaProfile.name) {
            KikobaProfileScreen(
                kikobaViewModel = kikobaViewModel,
                kikobaManagementViewModel = kikobaManagementViewModel
            )
        }

        /** A navhost for the screen that display a list of members of a particular kikoba */
        composable(route = AppRoutes.ManageLeaders.name) {
            ManageLeadersScreen(
                kikobaViewModel = kikobaViewModel,
                kikobaManagementViewModel = kikobaManagementViewModel
            )
        }

        /** A navhost for the screen that display leadersof a particular kikoba */
        composable(route = AppRoutes.Leaders.name) {
            KikobaLeadersScreen(
                kikobaViewModel = kikobaViewModel,
            )
        }

        /** A navhost for the screen that display a form for requesting a loan */
        composable(route = AppRoutes.RequestLoan.name) {
            RequestLoanScreen(
                loanViewModel = loanViewModel,
                kikobaViewModel = kikobaViewModel
            )
        }

        /** A navhost for the screen that display all requested loans of a particular kikoba member */
        composable(route = AppRoutes.MyLoans.name) {
            MyLoansScreen(
                loanViewModel = loanViewModel,
            )
        }

        /** A navhost for the screen that display members loans for being managed by secretary */
        composable(route = AppRoutes.ManageLoans.name) {
            ManageLoansScreen(
                loanViewModel = loanViewModel,
                navHostController = navHostController
            )
        }

        /** A navhost for the screen that display list of loans requested by members of a particular kikoba */
        composable(route = AppRoutes.MembersLoans.name) {
            MembersLoansScreen(
                loanViewModel = loanViewModel,
            )
        }

        /** A navhost for the screen that display list of loans requested by members of a particular kikoba */
        composable(route = AppRoutes.LoanInfo.name) {
            LoanInfoScreen(
                loanViewModel = loanViewModel,
                kikobaViewModel = kikobaViewModel,
            )
        }

        /** A navhost for the screen that display list of loans to be approved by chairperson */
        composable(route = AppRoutes.ApproveLoans.name) {
            ApproveLoansScreen(
                loanViewModel = loanViewModel,
                kikobaViewModel = kikobaViewModel
            )
        }

        /** A navhost for the screen that display list of loans to be approved by chairperson */
        composable(route = AppRoutes.LoansTransaction.name) {
            LoansTransactionScreen(
                loanViewModel = loanViewModel,
                kikobaViewModel = kikobaViewModel
            )
        }

        /** A navhost for the screen that display list of loans to be approved by chairperson */
        composable(route = AppRoutes.BuyShare.name) {
            BuyShareScreen(
                shareViewModel = shareViewModel,
                kikobaViewModel = kikobaViewModel
            )
        }

        /** A navhost for the screen that display list of shares contributed by kikoba member */
        composable(route = AppRoutes.MembersShares.name) {
            MembersSharesScreen(
                shareViewModel = shareViewModel,
                kikobaViewModel = kikobaViewModel
            )
        }

        /** A navhost for the screen that display list of shares for a particular user*/
        composable(route = AppRoutes.MyShares.name) {
            MySharesScreen(
                shareViewModel = shareViewModel,
                kikobaViewModel = kikobaViewModel
            )
        }

        /** A navhost for the chat screen used by members of kikoba */
        composable(route = AppRoutes.KikobaChat.name) {
            KikobaChatScreen(
            )
        }

        /** A navhost for the screen that display all information of a particular kikoba */
        composable(route = AppRoutes.AboutKikoba.name) {
            AboutKikobaScreen(
                kikobaViewModel = kikobaViewModel
            )
        }

        /** A navhost for the screen that display all debts of a particular kikoba */
        composable(route = AppRoutes.MyDebts.name) {
            MyDebtsScreen(
                debtViewModel = debtViewModel,
                kikobaViewModel = kikobaViewModel,
            )
        }

        /** A navhost for the screen that display all debts of all kikoba members */
        composable(route = AppRoutes.MembersDebts.name) {
            MembersDebtsScreen(
                debtViewModel = debtViewModel,
                //kikobaViewModel = kikobaViewModel
            )
        }
    }

}

/**
 * Resets the [OrderUiState] and pops up to [CupcakeScreen.Start]
 */
private fun cancelOrderAndNavigateToStart(
    navController: NavHostController
) {
    navController.popBackStack(AppRoutes.Login.name, inclusive = false)
}

/**
 * Creates an intent to share order details
 */
private fun shareOrder(context: Context, subject: String, summary: String) {
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent, context.getString(R.string.new_cupcake_order)
        )
    )
}
