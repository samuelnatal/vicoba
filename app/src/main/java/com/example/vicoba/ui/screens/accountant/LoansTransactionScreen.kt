package com.example.vicoba.ui.screens.accountant

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
import com.example.vicoba.data.models.keys.AccountantLoanKey
import com.example.vicoba.ui.components.popups.ActionAlertDialog
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.viewmodels.KikobaViewModel
import com.example.vicoba.ui.viewmodels.LoanViewModel

@Composable
fun LoansTransactionScreen(
    loanViewModel: LoanViewModel,
    kikobaViewModel: KikobaViewModel
) {


    val user = KikobaUserState.userState.collectAsState().value.user
    val activeKikoba = kikobaViewModel.activeKikoba.collectAsState().value

    /** A list of loans requested by kikoba members*/
    val membersLoans = loanViewModel.membersLoans.value

    /** Sort members loans to find all pending requested to be managed by a secretary */
    val approvedLoans = membersLoans.filter { it.status.contains("approved") }

    if (approvedLoans.isNotEmpty()) {
        LazyColumn() {
            items(approvedLoans) { item ->
                var paid by remember { mutableStateOf(false) }
                var onPayBtnClicked by remember { mutableStateOf(false) }

                Card() {
                    Row {
                        Text("Name: ${item.borrowerFirstName} ${item.borrowerLastName}")
                        Spacer(modifier = Modifier.weight(1f))
                        Text("Date: ${item.requestedAt}")
                    }

                    Row {
                        Text("Amount: ${item.loanAmount} Tsh")
                        Spacer(modifier = Modifier.weight(1f))
                        if (paid) Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Loan paid"
                        )


                        if (!paid) Button(
                            onClick = { onPayBtnClicked = true }
                        ) {
                            Text(
                                text = "Pay",
                            )
                        }
                        if (onPayBtnClicked) {
                            ActionAlertDialog(
                                title = "Pay loan.",
                                body = "Are you sure you want to pay this loan?",
                                showDialog = onPayBtnClicked,
                                onAccept = {
                                    val updatedWallet = activeKikoba.kikobaWallet - item.loanAmount
                                    loanViewModel.payLoan(
                                        accountantLoanKey = AccountantLoanKey(
                                            loanID = item.loanID,
                                            accountantID = user.userID,
                                            date = item.requestedAt,
                                            kikobaID = activeKikoba.kikobaID,
                                            kikobaName = activeKikoba.kikobaName,
                                            borrowerUserID = item.borrowerUserID,
                                            borrowerMemberID = item.borrowerMemberID,
                                            updatedWallet = updatedWallet,
                                            debtAmount = item.loanAmount
                                        )
                                    )

                                    paid = true
                                    onPayBtnClicked = false
                                },
                                onDismiss = { showDialog ->
                                    onPayBtnClicked = showDialog
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
