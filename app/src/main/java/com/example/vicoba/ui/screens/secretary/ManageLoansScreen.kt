package com.example.vicoba.ui.screens.secretary

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vicoba.R
import com.example.vicoba.data.models.keys.LoanID
import com.example.vicoba.ui.navigation.AppRoutes
import com.example.vicoba.ui.viewmodels.LoanViewModel

@Composable
fun ManageLoansScreen(
    loanViewModel: LoanViewModel,
    navHostController: NavHostController
) {
    /** A list of loans requested by kikoba members*/
    val membersLoans = loanViewModel.membersLoans.value

    /** Sort members loans to find all pending requested to be managed by a secretary */
    val pendingLoans = membersLoans.filter { it.status.contains("pending") }

    if (pendingLoans.isNotEmpty()) {
        LazyColumn() {
            items(membersLoans) { item ->
                Card() {
                    Row {
                        Text("Name: ${item.borrowerFirstName} ${item.borrowerLastName}")
                        Spacer(modifier = Modifier.weight(1f))
                        Text("Date: ${item.requestedAt}")
                    }

                    Row {
                        Text("Amount: ${item.loanAmount} Tsh")
                        Spacer(modifier = Modifier.weight(1f))
                        Text("Status: ${item.status}")
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {

                            loanViewModel.getLoanInfo(
                                loanID = LoanID(
                                    loanID = item.loanID
                                )
                            )

                            navHostController.navigate(AppRoutes.LoanInfo.name)

                        }) {
                            Icon(
                                painter = painterResource(R.drawable.visibility),
                                contentDescription = "View loan"
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    } else {
        Text(text = "No any new requested loan.")
    }
}
