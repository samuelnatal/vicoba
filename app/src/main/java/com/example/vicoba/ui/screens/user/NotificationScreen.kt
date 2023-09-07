package com.example.vicoba.ui.screens.user

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.vicoba.R
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.keys.NotificationID
import com.example.vicoba.ui.components.NotificationCard
import com.example.vicoba.ui.navigation.AppRoutes
import com.example.vicoba.ui.viewmodels.KikobaViewModel
import com.example.vicoba.ui.viewmodels.NotificationViewModel
import com.example.vicoba.utility.formatDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationScreen(
    kikobaViewModel: KikobaViewModel,
    notificationViewModel: NotificationViewModel,
    navHostController: NavHostController
) {
    /** Get the list of notifications from the view model */
    val notifications = notificationViewModel.notifications.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.light_grayish_blue))
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.light_grayish_blue))
            ) {
                if (notifications.isEmpty()) {
                    Text(text = "You don't have any notification currently.")
                } else {
                    LazyColumn {
                        itemsIndexed(notifications) { _, userNotification ->
                            Spacer(modifier = Modifier.height(2.dp))
                            if (userNotification.notificationTitle == "Invitation.") {
                                val notificationID = NotificationID(
                                    notificationID = userNotification.notificationID
                                )
                                NotificationCard(
                                    leftIcon = Icons.Default.Notifications,
                                    rightIcon = painterResource(R.drawable.visibility),
                                    subRightIcon = painterResource(R.drawable.expandless),
                                    type = "show",
                                    title = userNotification.notificationTitle,
                                    body = userNotification.notificationBody,
                                    time = formatDate(userNotification.notifiedDate),
                                    notificationID = notificationID,
                                    notificationViewModel = notificationViewModel,
                                    action = {

                                        kikobaViewModel.getInvitedKikobaInfo(
                                            kikobaID = KikobaID(
                                                kikobaID = userNotification.kikobaInvolved
                                            )
                                        )

                                        kikobaViewModel.getKikobaMembers(
                                            kikobaID = KikobaID(
                                                kikobaID = userNotification.kikobaInvolved
                                            )
                                        )

                                        kikobaViewModel.getTotalKikobaMembers(
                                            kikobaID = KikobaID(
                                                kikobaID = userNotification.kikobaInvolved
                                            )
                                        )

                                        notificationViewModel.setNotificationID(userNotification.notificationID)

                                        navHostController.navigate(AppRoutes.InvitedKikoba.name)
                                    }
                                )

                            } else {
                                val notificationID = NotificationID(
                                    notificationID = userNotification.notificationID
                                )
                                NotificationCard(
                                    type = "more",
                                    leftIcon = Icons.Default.Notifications,
                                    rightIcon = painterResource(R.drawable.expandmore),
                                    subRightIcon = painterResource(R.drawable.expandless),
                                    title = userNotification.notificationTitle,
                                    body = userNotification.notificationBody,
                                    time = formatDate(userNotification.notifiedDate),
                                    action = { },
                                    notificationID = notificationID,
                                    notificationViewModel = notificationViewModel
                                )
                            }
                            Spacer(modifier = Modifier.height(1.dp))
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun NotificationScreenPreview() {
    val notificationViewModel: NotificationViewModel =
        viewModel(factory = NotificationViewModel.notificationFactory)

    val kikobaViewModel: KikobaViewModel =
        viewModel(factory = KikobaViewModel.kikobaFactory)
    NotificationScreen(
        notificationViewModel = notificationViewModel,
        kikobaViewModel = kikobaViewModel,
        navHostController = NavHostController(LocalContext.current)
    )
}