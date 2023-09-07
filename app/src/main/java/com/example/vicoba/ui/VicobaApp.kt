package com.example.vicoba.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vicoba.ui.components.bars.VicobaBottomAppBar
import com.example.vicoba.ui.components.bars.VicobaTopAppBar
import com.example.vicoba.ui.navigation.AppRoutes
import com.example.vicoba.ui.navigation.NavGraph
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VicobaApp(
    regViewModel: RegistrationViewModel,
    loginViewModel: LoginViewModel,
    kikobaViewModel: KikobaViewModel,
    kikobaManagementViewModel: KikobaManagementViewModel,
    notificationViewModel: NotificationViewModel,
    sessionViewModel: SessionViewModel,
    searchViewModel: SearchViewModel,
    loanViewModel:LoanViewModel,
    shareViewModel: ShareViewModel,
    debtViewModel: DebtViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    /** backStackEntry variable observes the backet stack of the screen during navigation*/
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppRoutes.valueOf(
        backStackEntry?.destination?.route ?: AppRoutes.Login.name
    )

    /** Get the current route name */
    val current = navController.currentBackStackEntryAsState().value?.destination?.route

    val active = sessionViewModel.userActive.value

    Scaffold(
        topBar = {
            val screenNames = setOf("Home", "Login")
            VicobaTopAppBar(currentScreen = currentScreen,
                canNavigateBack = !screenNames.contains(current),
                navigateUp = { navController.navigateUp() })
        },

        bottomBar = {
            if (active) {
                VicobaBottomAppBar(
                    notificationViewModel = notificationViewModel,
                    navController = navController
                )
            }
        },
        modifier = modifier.fillMaxSize(),

        ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {

            NavGraph(
                regViewModel = regViewModel,
                loginViewModel = loginViewModel,
                kikobaViewModel = kikobaViewModel,
                kikobaManagementViewModel = kikobaManagementViewModel,
                notificationViewModel = notificationViewModel,
                sessionViewModel = sessionViewModel,
                searchViewModel = searchViewModel,
                loanViewModel = loanViewModel,
                shareViewModel = shareViewModel,
                debtViewModel = debtViewModel,
                navHostController = navController,
                modifier = Modifier
            )
        }
    }
}


