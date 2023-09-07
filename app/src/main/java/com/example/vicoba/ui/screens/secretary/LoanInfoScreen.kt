package com.example.vicoba.ui.screens.secretary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.vicoba.R
import com.example.vicoba.data.models.keys.SecretaryLoanKey
import com.example.vicoba.ui.components.popups.ActionAlertDialog
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.viewmodels.KikobaViewModel
import com.example.vicoba.ui.viewmodels.LoanViewModel

@Composable
fun LoanInfoScreen(
    loanViewModel: LoanViewModel,
    kikobaViewModel: KikobaViewModel,
) {
    var rejected by remember { mutableStateOf(false) }
    var onRejectedBtnClicked by remember { mutableStateOf(false) }
    var onAcceptBtnClicked by remember { mutableStateOf(false) }
    var accepted by remember { mutableStateOf(false) }

    val activeKikoba = kikobaViewModel.activeKikoba.collectAsState().value

    val loan = loanViewModel.loanInfo.value
    val user = KikobaUserState.userState.collectAsState().value.user

    Column() {
        Text(text = "MUOMBAJI: ${loan.borrowerFirstName} ${loan.borrowerLastName}")
        Text(text = "KIASI: ${loan.loanAmount} Tsh")
        Text(text = "UFAFANUZI WA MKOPO: ${loan.loanDesc}")
        Text(text = "MDHAMANA: ${loan.guarantee}")
        Text(text = "TAREHE: ${loan.requestedAt}")
        Text(text = "MDHAMINI WA KWANZA: ${loan.guarantor1FirstName} ${loan.guarantor1LastName}")
        Text(text = "MDHAMINI WA PILI: ${loan.guarantor2FirstName} ${loan.guarantor2LastName}")
        Text(text = "MDHAMINI WA TATU: ${loan.guarantor3FirstName} ${loan.guarantor3LastName}")

        Spacer(modifier = Modifier.height(20.dp))


        Row() {
            if (!rejected) {
                Button(
                    onClick = {
                        onRejectedBtnClicked = true
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

            if (onRejectedBtnClicked) {
                ActionAlertDialog(
                    title = "Reject loan ",
                    body = "Are you sure you want to reject this loan?",
                    showDialog = onRejectedBtnClicked,
                    onAccept = {
                        loanViewModel.rejectLoan(
                            secretaryLoanKey = SecretaryLoanKey(
                                loanID = loan.loanID,
                                borrowerUserID = loan.borrowerUserID,
                                date = loan.requestedAt,
                                kikobaID = activeKikoba.kikobaID,
                                kikobaName = activeKikoba.kikobaName,
                                secretaryID = user.userID
                            )
                        )
                        onRejectedBtnClicked = false
                        rejected = true
                    },
                    onDismiss = { showDialogStatus ->
                        onRejectedBtnClicked = showDialogStatus
                    }
                )
            }

            Spacer(modifier = Modifier.width(1.dp))

            if (!accepted) {
                Button(
                    onClick = {
                        onAcceptBtnClicked = true
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
                        text = "Accepted",
                    )
                }
            }

            if (onAcceptBtnClicked) {
                ActionAlertDialog(
                    title = "Accept loan ",
                    body = "Are you sure you want to accept this loan?",
                    showDialog = onAcceptBtnClicked,
                    onAccept = {
                        loanViewModel.acceptLoan(
                            secretaryLoanKey = SecretaryLoanKey(
                                loanID = loan.loanID,
                                borrowerUserID = loan.borrowerUserID,
                                date = loan.requestedAt,
                                kikobaID = activeKikoba.kikobaID,
                                kikobaName = activeKikoba.kikobaName,
                                secretaryID = user.userID
                            )
                        )
                        onAcceptBtnClicked = false
                        accepted = true
                    },
                    onDismiss = { showDialogStatus ->
                        onAcceptBtnClicked = showDialogStatus
                    }
                )
            }
        }
    }
}