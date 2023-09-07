package com.example.vicoba.ui.screens.chairPerson

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vicoba.data.models.keys.ChairPersonLoanKey
import com.example.vicoba.ui.components.popups.ActionAlertDialog
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.viewmodels.KikobaViewModel
import com.example.vicoba.ui.viewmodels.LoanViewModel

@Composable
fun ApproveLoansScreen(
    loanViewModel: LoanViewModel,
    kikobaViewModel: KikobaViewModel
) {


    val user = KikobaUserState.userState.collectAsState().value.user
    val activeKikoba = kikobaViewModel.activeKikoba.collectAsState().value

    /** A list of loans requested by kikoba members*/
    val membersLoans = loanViewModel.membersLoans.value

    /** Sort members loans to find all pending requested to be managed by a secretary */
    val acceptedLoans = membersLoans.filter { it.status.contains("accepted") }

    if (acceptedLoans.isNotEmpty()) {
        LazyColumn() {
            items(acceptedLoans) { item ->
                var approved by remember { mutableStateOf(false) }
                var onApproveBtnClicked by remember { mutableStateOf(false) }

                Card() {
                    Row {
                        Text("Name: ${item.borrowerFirstName} ${item.borrowerLastName}")
                        Spacer(modifier = Modifier.weight(1f))
                        Text("Date: ${item.requestedAt}")
                    }

                    Row {
                        Text("Amount: ${item.loanAmount} Tsh")
                        Spacer(modifier = Modifier.weight(1f))
                        if (approved) Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Loan approved"
                        )


                        if (!approved) Button(
                            onClick = { onApproveBtnClicked = true }
                        ) {
                            Text(
                                text = "Approve",
                            )
                        }
                        if (onApproveBtnClicked) {
                            ActionAlertDialog(
                                title = "Approve loan.",
                                body = "Are you sure you want to approve this loan?",
                                showDialog = onApproveBtnClicked,
                                onAccept = {
                                    loanViewModel.approveLoan(
                                        chairPersonLoanKey = ChairPersonLoanKey(
                                            loanID = item.loanID,
                                            chairPersonID = user.userID,
                                            date = item.requestedAt,
                                            kikobaID = activeKikoba.kikobaID,
                                            kikobaName = activeKikoba.kikobaName,
                                            borrowerUserID = item.borrowerUserID
                                        )
                                    )

                                    approved = true
                                    onApproveBtnClicked = false
                                },
                                onDismiss = { showDialog ->
                                    onApproveBtnClicked = showDialog
                                }
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    } else {
        Text(text = "No loans to be approved.")
    }
}
