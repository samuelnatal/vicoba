package com.example.vicoba.ui.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import com.example.vicoba.ui.components.users.accountant.SelectAccountantCard
import com.example.vicoba.ui.components.users.admin.SelectAdminCard
import com.example.vicoba.ui.components.users.chairperson.SelectChairPersonCard
import com.example.vicoba.ui.components.users.secretary.SelectSecretaryCard
import com.example.vicoba.ui.viewmodels.KikobaManagementViewModel
import com.example.vicoba.ui.viewmodels.KikobaViewModel


@Composable
fun ManageLeadersScreen(
    kikobaViewModel: KikobaViewModel,
    kikobaManagementViewModel: KikobaManagementViewModel
) {
    val members = kikobaViewModel.members.value
    val scrollState = rememberScrollState()
    Column(
        modifier = androidx.compose.ui.Modifier
            .verticalScroll(scrollState) // Enable vertical scrolling
            .fillMaxSize()
    ) {
        SelectAdminCard(
            members = members,
            kikobaViewModel = kikobaViewModel,
            kikobaManagementViewModel = kikobaManagementViewModel
        )
        SelectSecretaryCard(
            members = members,
            kikobaViewModel = kikobaViewModel,
            kikobaManagementViewModel = kikobaManagementViewModel
        )
        SelectAccountantCard(
            members = members,
            kikobaViewModel = kikobaViewModel,
            kikobaManagementViewModel = kikobaManagementViewModel
        )
        SelectChairPersonCard(
            members = members,
            kikobaViewModel = kikobaViewModel,
            kikobaManagementViewModel = kikobaManagementViewModel
        )
    }

}


