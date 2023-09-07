package com.example.vicoba.ui.screens.member

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vicoba.ui.viewmodels.LoanViewModel

@Composable
fun MembersLoansScreen(
    loanViewModel: LoanViewModel
) {
    val membersLoans = loanViewModel.membersLoans.value

    if(membersLoans.isNotEmpty()){
        LazyColumn(){
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
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }else{
        Text(text="Hakuna mikopo yoyote kwa sasa.")
    }
}
