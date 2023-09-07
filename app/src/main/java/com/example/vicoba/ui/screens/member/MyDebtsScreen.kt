package com.example.vicoba.ui.screens.member

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vicoba.data.models.items.DebtCoupon
import com.example.vicoba.ui.components.popups.ActionAlertDialog
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.viewmodels.DebtViewModel
import com.example.vicoba.ui.viewmodels.KikobaViewModel

@Composable
fun MyDebtsScreen(
    debtViewModel: DebtViewModel,
    kikobaViewModel: KikobaViewModel,
) {
    /** activeKikoba variable stores information of the current active kikoba */
    val activeKikoba = kikobaViewModel.activeKikoba.collectAsState().value

    val membersDebts = debtViewModel.membersDebts.value

    /** user variable holds the information of the active user */
    val user = KikobaUserState.userState.collectAsState().value.user

    /** Sort members loans to find all pending requested to be managed by a secretary */
    val myDebts =
        membersDebts.filter { it.debtorUserID == user.userID && it.debtStatus.contains("Halijalipwa") }


    if (myDebts.isNotEmpty()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
        ) {

            items(myDebts) { debt ->
                var onPayDebtBtnClicked by remember { mutableStateOf(false) }
                var debtPaid by remember { mutableStateOf(false) }
                Card() {
                    Column() {


                        Row {
                            Text(text = "Kiasi: ${debt.debtAmount} Tsh")
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "Date: ${debt.date}")
                        }

                        Row {
                            Text(text = "Maelezo:${debt.debtDesc}")
                            Spacer(modifier = Modifier.weight(1f))

                            if (!debtPaid) OutlinedButton(
                                onClick = {
                                    onPayDebtBtnClicked = true
                                }
                            ) {
                                Text(text = "Lipa")
                            } else Button(
                                onClick = {},
                            )
                            {
                                Text(text = "Deni limelipwa.")
                            }
                        }
                    }
                }
                ActionAlertDialog(
                    title = "Malipo ya deni",
                    body = "Lipa deni lenye thamani ya shiling ${debt.debtAmount} ulilokopa tarehe ${debt.date}",
                    showDialog = onPayDebtBtnClicked,
                    onAccept = {

                        val updatedWallet = debt.debtAmount + activeKikoba.kikobaWallet

                        debtViewModel.payDebt(
                            debtCoupon = DebtCoupon(
                                userID = user.userID,
                                debtID = debt.debtID,
                                kikobaID = activeKikoba.kikobaID,
                                kikobaName = activeKikoba.kikobaName,
                                updatedWallet = updatedWallet,
                                debtAmount = debt.debtAmount
                            )
                        )
                        debtPaid = true
                        onPayDebtBtnClicked = false
                    },
                    onDismiss = { showDialog ->
                        onPayDebtBtnClicked = showDialog

                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    } else {
        Text(text = "Hauna deni lolote kwa sasa.")
    }
}


