package com.example.vicoba.ui.screens.kikoba

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vicoba.R
import com.example.vicoba.data.models.keys.MemberID
import com.example.vicoba.ui.components.DashboardTopCard
import com.example.vicoba.ui.components.users.accountant.AccountantAdministrationCard
import com.example.vicoba.ui.components.users.admin.AdminAdministrationCard
import com.example.vicoba.ui.components.users.chairperson.ChairPersonAdministrationCard
import com.example.vicoba.ui.components.users.member.MemberAdministrationCard
import com.example.vicoba.ui.components.users.secretary.SecretaryAdministrationCard
import com.example.vicoba.ui.navigation.AppRoutes
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.viewmodels.KikobaManagementViewModel
import com.example.vicoba.ui.viewmodels.KikobaViewModel
import com.example.vicoba.ui.viewmodels.LoanViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    kikobaViewModel: KikobaViewModel,
    kikobaManagementViewModel: KikobaManagementViewModel,
    loanViewModel: LoanViewModel,
    navHostController: NavHostController
) {
    /** activeKikoba variable stores information of the current active kikoba */
    val activeKikoba = kikobaViewModel.activeKikoba.collectAsState().value

    /** user variable holds the information of the active user */
    val user = KikobaUserState.userState.collectAsState()

    val memberID = kikobaViewModel.kikobaMemberID.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        DashboardTopCard(
            kikobaName = activeKikoba.kikobaName,
            kikobaWallet = activeKikoba.kikobaWallet,
        )
        Column(
            modifier = Modifier
                .padding(20.dp)
                .border(1.dp, Color.LightGray)
        ) {

            Divider(color = colorResource(id = R.color.min_gray), thickness = 0.2.dp)

            if (user.value.user.userID == activeKikoba.kikobaAdmin) {
                AdminAdministrationCard(
                    activeKikoba = activeKikoba,
                    kikobaViewModel = kikobaViewModel,
                    kikobaManagementViewModel = kikobaManagementViewModel,
                    navHostController = navHostController
                )
            }

            if (user.value.user.userID == activeKikoba.kikobaSecretaryID) {
                SecretaryAdministrationCard(
                    navHostController = navHostController
                )
            }

            if (user.value.user.userID == activeKikoba.kikobaAccountantID) {
                AccountantAdministrationCard(
                    navHostController = navHostController
                )
            }

            if (user.value.user.userID == activeKikoba.kikobaChairPersonID) {
                ChairPersonAdministrationCard(
                    navHostController = navHostController
                )
            }
        }

        MemberAdministrationCard(navHostController = navHostController)

        KikobaComponents(
            memberID = memberID,
            navHostController = navHostController,
            loanViewModel = loanViewModel,
        )
    }
}

@Composable
fun KikobaComponents(
    memberID: Int,
    loanViewModel: LoanViewModel,
    navHostController: NavHostController
) {
    val cards = listOf(
        CardItem(
            imageResId = R.drawable.stocks,
            text = "Hisa zangu",
            action = {
                navHostController.navigate(AppRoutes.MyShares.name)
            }
        ),

        CardItem(
            imageResId = R.drawable.stocks,
            text = "Hisa zote.",
            action = {
                navHostController.navigate(AppRoutes.MembersShares.name)
            }
        ),

        CardItem(
            imageResId = R.drawable.members,
            text = "Wanakikundi",
            action = {
                navHostController.navigate(AppRoutes.KikobaMembers.name)
            }
        ),

        CardItem(
            imageResId = R.drawable.members,
            text = "Viongozi",
            action = {
                navHostController.navigate(AppRoutes.Leaders.name)
            }
        ),

        CardItem(
            imageResId = R.drawable.money_bag,
            text = "Mikopo yangu",
            action = {
                loanViewModel.getMyLoans(
                    memberID = MemberID(
                        memberID = memberID
                    )
                )
                navHostController.navigate(AppRoutes.MyLoans.name)
            }),

        CardItem(
            imageResId = R.drawable.chat,
            text = "Makutano",
            action = {
                navHostController.navigate(AppRoutes.KikobaChat.name)
            }
        ),

        CardItem(
            imageResId = R.drawable.money_bag,
            text = "Mikopo yote",
            action = {
                navHostController.navigate(AppRoutes.MembersLoans.name)
            }
        ),

        CardItem(
            imageResId = R.drawable.money_bag,
            text = "Madeni yote",
            action = {
                navHostController.navigate(AppRoutes.MembersDebts.name)
            }
        ),

        CardItem(
            imageResId = R.drawable.stocks,
            text = "Kuhusu kikoba",
            action = {
                navHostController.navigate(AppRoutes.AboutKikoba.name)
            }
        ),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(cards) { card ->
            CardItem(card)
        }
    }
}

@Composable
fun CardItem(card: CardItem) {
    Card(
        modifier = Modifier
            .clickable {
                card.action()
            }
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray)

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Image(
                painter = painterResource(card.imageResId),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = card.text,
            )
        }
    }
}


data class CardItem(val imageResId: Int, val text: String, val action: () -> Unit)
