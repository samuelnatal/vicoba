package com.example.vicoba.ui.screens.kikoba

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.vicoba.R
import com.example.vicoba.data.models.items.InvitationCoupon
import com.example.vicoba.ui.components.DisplayMemberCard
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.viewmodels.KikobaViewModel
import com.example.vicoba.ui.viewmodels.NotificationViewModel

@Composable
fun KikobaInvitationScreen(
    kikobaViewModel: KikobaViewModel,
    notificationViewModel: NotificationViewModel,
) {
    val invitedKikoba = kikobaViewModel.invitedKikoba.collectAsState().value
    val rejectionStatus = kikobaViewModel.invitationRejectionStatus.collectAsState().value
    val acceptationStatus = kikobaViewModel.invitationAcceptationStatus.collectAsState().value
    val members = kikobaViewModel.members.value
    val totalMembers = kikobaViewModel.totalMembers.value
    val user = KikobaUserState.userState.collectAsState().value.user
    val notificationID = notificationViewModel.currentNotificationID.collectAsState().value

    Column() {
        Text(text = "ID: ${invitedKikoba.kikobaID}")
        Text(text = "KIKOBA NAME: ${invitedKikoba.kikobaName}")
        Text(text = "KIKOBA DESCRIPTION: ${invitedKikoba.kikobaDesc}")
        Text(text = "CREATED AT: ${invitedKikoba.createdAt}")
        Text(text = "OWNER FIRST NAME: ${invitedKikoba.ownerFirstName}")
        Text(text = "OWNER LAST NAME: ${invitedKikoba.ownerLastName}")
        Text(text = "REGION: ${invitedKikoba.region}")
        Text(text = "DISTRICT: ${invitedKikoba.district}")
        Text(text = "WARD: ${invitedKikoba.ward}")

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "TOTAL MEMBERS: ${totalMembers.total}")

        if (members.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(members) { member ->
                    DisplayMemberCard(member = member)
                }
            }
        } else {
            Text(text = "No members found at this kikoba")
        }

        Row() {
            if (!rejectionStatus) {
                Button(
                    onClick = {
                        kikobaViewModel.rejectKikobaInvitation(
                            invitationCoupon = InvitationCoupon(
                                userID = user.userID,
                                kikobaID = invitedKikoba.kikobaID,
                                kikobaName = invitedKikoba.kikobaName,
                                userFirstName = user.firstName,
                                userLastName = user.lastName,
                                kikobaAdmin = invitedKikoba.kikobaAdmin,
                                notificationID = notificationID
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = MaterialTheme.shapes.medium,
                        )

                ) {
                    Text(
                        text = "Reject",
                        color = colorResource(id = R.color.black)
                    )
                }
            } else {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark_gray)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = MaterialTheme.shapes.medium,
                        )

                ) {
                    Text(
                        text = "Rejected",
                    )
                }
            }

            Spacer(modifier = Modifier.width(1.dp))

            if (!acceptationStatus) {
                Button(
                    onClick = {
                        kikobaViewModel.acceptKikobaInvitation(
                            invitationCoupon = InvitationCoupon(
                                userID = user.userID,
                                kikobaID = invitedKikoba.kikobaID,
                                kikobaName = invitedKikoba.kikobaName,
                                userFirstName = user.firstName,
                                userLastName = user.lastName,
                                kikobaAdmin = invitedKikoba.kikobaAdmin,
                                notificationID = notificationID
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_color)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = MaterialTheme.shapes.medium,
                        )

                ) {
                    Text(
                        text = "Accept",
                        color = colorResource(id = R.color.black)
                    )
                }
            } else {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark_green)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = MaterialTheme.shapes.medium,
                        )

                ) {
                    Text(
                        text = "Member",
                    )
                }
            }
        }
    }
}