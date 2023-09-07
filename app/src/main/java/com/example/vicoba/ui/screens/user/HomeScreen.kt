package com.example.vicoba.ui.screens.user

import OkAlertDialog
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vicoba.R
import com.example.vicoba.data.constants.Membership
import com.example.vicoba.data.models.entities.KikobaRequestCredentials
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.keys.KikobaIDUserID
import com.example.vicoba.data.models.lists.VikobaNearUser
import com.example.vicoba.data.models.lists.VikobaUserInvolvedIn
import com.example.vicoba.data.models.lists.vikobaNearUser
import com.example.vicoba.data.models.lists.vikobaUserInvolved
import com.example.vicoba.ui.navigation.AppRoutes
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.viewmodels.DebtViewModel
import com.example.vicoba.ui.viewmodels.KikobaViewModel
import com.example.vicoba.ui.viewmodels.LoanViewModel
import com.example.vicoba.ui.viewmodels.ShareViewModel


/**
 * This composable expects [orderUiState] that represents the order state, [onCancelButtonClicked]
 * lambda that triggers canceling the order and passes the final order to [onSendButtonClicked]
 * lambda
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    kikobaViewModel: KikobaViewModel,
    navController: NavHostController,
    loanViewModel: LoanViewModel,
    shareViewModel: ShareViewModel,
    debtViewModel: DebtViewModel,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    /** user variable holds the information of the active user */
    val user = KikobaUserState.userState.collectAsState().value.user

    /** Below are the states observing the request action of joining kikoba */
    val requestStatus by kikobaViewModel.requestStatus.collectAsState()
    var requested by rememberSaveable { mutableStateOf(requestStatus) }

    /** Called when request button is triggered inorder to update the UI */
    val onRequest = fun() {
        requested = true
    }

    /** The variable below holds the lists of vikoba a particular user is involved in */
    vikobaUserInvolved

    /** The variable below holds the lists of vikoba near particular user */
    vikobaNearUser

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.light_grayish_blue))
    ) {
        DashboardTopCard(
            welcome = "${user.firstName} ${user.lastName}",
        )

        Column(
            modifier = Modifier.verticalScroll(scrollState),
        ) {
            Button(onClick = { navController.navigate(AppRoutes.CreateKicoba.name) }) {
                Text(text = "Fungua kikoba")
            }
            VikobaUserInvolvedInCard(
                userID = user.userID,
                vikobaUserInvolvedIn = vikobaUserInvolved,
                kikobaViewModel = kikobaViewModel,
                navController = navController,
                loanViewModel = loanViewModel,
                shareViewModel = shareViewModel,
                debtViewModel = debtViewModel
            )
            VicobaNearYouCard(
                userID = user.userID,
                onRequest = onRequest,
                vikobaNearUser = vikobaNearUser,
                kikobaViewModel = kikobaViewModel
            )
        }
    }

}

@Composable
fun VicobaNearYouCard(
    userID: Int,
    onRequest: () -> Unit,
    vikobaNearUser: List<VikobaNearUser>,
    kikobaViewModel: KikobaViewModel,
) {
    Column(
        Modifier.padding(20.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.kikoba_near_text),
                color = colorResource(id = R.color.dark_green),
                style = MaterialTheme.typography.bodyLarge,
            )
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Zaidi",
                    color = colorResource(id = R.color.dark_green),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        if (vikobaNearUser.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                vikobaNearUser.forEach { item ->
                    when (item.status) {

                        Membership.Request.Approved.name -> ExceptionCard(
                            name = item.kikobaName,
                            status = "Member",
                            color = colorResource(id = R.color.dark_green)
                        )

                        Membership.Request.Unrequested.name -> UnrequestedCard(
                            kikobaViewModel = kikobaViewModel,
                            name = item.kikobaName,
                            userID = userID,
                            kikobaID = item.kikobaID,
                            onRequest = onRequest,
                        )

                        Membership.Request.Pending.name -> ExceptionCard(
                            name = item.kikobaName,
                            status = Membership.Request.Pending.name,
                            color = Color.DarkGray
                        )

                        Membership.Request.Rejected.name -> ExceptionCard(
                            name = item.kikobaName,
                            status = Membership.Request.Rejected.name,
                            color = Color.Red
                        )

                    }
                }
            }
        } else {
            Text(text = "There is no kikoba near you")
        }
    }
}


@Composable
fun UnrequestedCard(
    name: String,
    userID: Int,
    kikobaID: Int,
    onRequest: () -> Unit,
    kikobaViewModel: KikobaViewModel,
) {
    /** Observe the right time for showing success dialog after requesting to join kikoba */
    var showDialog by remember { mutableStateOf(false) }
    var requested by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp), modifier = Modifier.shadow(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name, color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.weight(1f))

            if (!requested) {
                Button(
                    onClick = {
                        val kikobaRequestCredentials = KikobaRequestCredentials(
                            userID = userID,
                            kikobaID = kikobaID,
                        )

                        kikobaViewModel.saveKikobaJoinRequest(kikobaRequestCredentials)
                        onRequest()
                        showDialog = true
                        requested = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.button_color),
                    )
                ) {
                    Text(text = "Request")
                }
            }

            if (requested) {
                Button(
                    onClick = {},
                    enabled = false,
                ) {
                    Text(text = "Requested")
                }
            }

        }

        OkAlertDialog(
            icon = Icons.Default.CheckCircle,
            title = "Successfully",
            body = "Request to join kikoba was sent successful",
            showDialog = showDialog,
            onDismiss = { showDialogStatus ->
                showDialog = showDialogStatus
            }
        )
    }
}


@Composable
fun ExceptionCard(
    name: String,
    status: String,
    color: Color,
) {

    Card(
        shape = RoundedCornerShape(8.dp), modifier = Modifier.shadow(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name, color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {},
                enabled = false,
            ) {
                Text(text = status, color = color)
            }
        }
    }
}


@Composable
fun VikobaUserInvolvedInCard(
    userID: Int,
    vikobaUserInvolvedIn: List<VikobaUserInvolvedIn>,
    kikobaViewModel: KikobaViewModel,
    loanViewModel: LoanViewModel,
    shareViewModel: ShareViewModel,
    debtViewModel: DebtViewModel,
    navController: NavHostController
) {
    Column(
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.involved_text),
                style = MaterialTheme.typography.bodyLarge,
                color = colorResource(id = R.color.greenish_teal)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (vikobaUserInvolvedIn.isNotEmpty()) {
            LazyRow(
                Modifier.height(160.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(vikobaUserInvolvedIn) { item ->
                    KikobaAmIn(
                        userID = userID,
                        kikobaID = item.kikobaID,
                        kikobaName = item.kikobaName,
                        kikobaLocation = "${item.kikobaRegion}, ${item.kikobaDistrict} ,${item.kikobaWard}",
                        kikobaViewModel = kikobaViewModel,
                        loanViewModel = loanViewModel,
                        shareViewModel = shareViewModel,
                        debtViewModel = debtViewModel,
                        navController = navController
                    )
                }
            }
        } else {
            Text(text = "You are currently not participating in any kikoba")
        }
    }
}


@Composable
fun KikobaAmIn(
    kikobaID: Int,
    userID: Int,
    kikobaName: String,
    kikobaLocation: String,
    navController: NavHostController,
    kikobaViewModel: KikobaViewModel,
    shareViewModel:ShareViewModel,
    loanViewModel:LoanViewModel,
    debtViewModel: DebtViewModel
) {
    Card(
        Modifier.width(300.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row() {
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = kikobaName,
                    color = colorResource(id = R.color.black),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = kikobaLocation,
                    color = colorResource(id = R.color.black),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Button(
                    onClick = {

                        kikobaViewModel.getKikobaInfo(
                            kikobaID = KikobaID(
                                kikobaID = kikobaID
                            )
                        )
                        kikobaViewModel.getKikobaMemberID(
                            kikobaIDUserID = KikobaIDUserID(
                                userID = userID,
                                kikobaID = kikobaID
                            )
                        )

                        kikobaViewModel.getKikobaMembers(
                            kikobaID = KikobaID(
                                kikobaID = kikobaID
                            )
                        )

                        loanViewModel.getMembersLoans(
                            kikobaID = KikobaID(
                                kikobaID = kikobaID
                            )
                        )

                        shareViewModel.getAllShares(
                            kikobaID = KikobaID(
                                kikobaID = kikobaID
                            )
                        )

                        debtViewModel.getAllDebts(
                            kikobaID = KikobaID(
                                kikobaID = kikobaID
                            )
                        )


                        navController.navigate(AppRoutes.Dashboard.name)
                    }
                ) {
                    Text(text = stringResource(id = R.string.view_text))
                }
            }
        }
    }
}


@Composable
fun DashboardTopCard(
    welcome: String,
    cornerRadius: Dp = 30.dp,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = cornerRadius, bottomEnd = cornerRadius
                )
            )
            .background(color = MaterialTheme.colorScheme.primaryContainer),

        ) {

        Text(
            text = "Karibu, $welcome",
            style = MaterialTheme.typography.headlineSmall,
        )

    }
}


@Preview
@Composable
fun DashboardScreenPreview() {
    val navController = rememberNavController()
    val kikobaViewModel: KikobaViewModel =
        viewModel(factory = KikobaViewModel.kikobaFactory)
    val loanViewModel: LoanViewModel =
        viewModel(factory = LoanViewModel.loanFactory)
    val shareViewModel: ShareViewModel =
        viewModel(factory = ShareViewModel.shareFactory)
    val debtViewModel: DebtViewModel =
        viewModel(factory = DebtViewModel.debtFactory)

    HomeScreen(
        kikobaViewModel = kikobaViewModel,
        navController = navController,
        loanViewModel = loanViewModel,
        shareViewModel = shareViewModel,
        debtViewModel = debtViewModel,
        modifier = Modifier.fillMaxSize()
    )
}
