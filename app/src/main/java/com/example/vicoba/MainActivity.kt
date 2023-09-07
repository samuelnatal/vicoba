package com.example.vicoba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vicoba.ui.VicobaApp
import com.example.vicoba.ui.theme.VicobaTheme
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VicobaTheme {
                /**
                 * Creating ViewModel instances by passing ViewModels Factory companion object to the viewModel call
                 */
                val regViewModel: RegistrationViewModel =
                    viewModel(factory = RegistrationViewModel.registrationFactory)

                val loginViewModel: LoginViewModel =
                    viewModel(factory = LoginViewModel.loginFactory)

                val kikobaViewModel: KikobaViewModel =
                    viewModel(factory = KikobaViewModel.kikobaFactory)

                val kikobaManagementViewModel: KikobaManagementViewModel =
                    viewModel(factory = KikobaManagementViewModel.kikobaManagementFactory)

                val notificationViewModel: NotificationViewModel =
                    viewModel(factory = NotificationViewModel.notificationFactory)

                val searchViewModel: SearchViewModel =
                    viewModel(factory = SearchViewModel.searchFactory)

                val loanViewModel: LoanViewModel =
                    viewModel(factory = LoanViewModel.loanFactory)

                val shareViewModel: ShareViewModel =
                    viewModel(factory = ShareViewModel.shareFactory)

                val debtViewModel: DebtViewModel =
                    viewModel(factory = DebtViewModel.debtFactory)

                val sessionViewModel = SessionViewModel()
                VicobaApp(
                    regViewModel = regViewModel,
                    loginViewModel = loginViewModel,
                    kikobaViewModel = kikobaViewModel,
                    kikobaManagementViewModel = kikobaManagementViewModel,
                    searchViewModel = searchViewModel,
                    notificationViewModel = notificationViewModel,
                    sessionViewModel = sessionViewModel,
                    loanViewModel = loanViewModel,
                    shareViewModel = shareViewModel,
                    debtViewModel = debtViewModel
                )
            }
        }
    }
}

