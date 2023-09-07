package com.example.vicoba.ui.screens.member

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vicoba.ui.viewmodels.DebtViewModel

@Composable
fun MembersDebtsScreen(
    debtViewModel: DebtViewModel,
) {

    val membersDebts = debtViewModel.membersDebts.value


    if (membersDebts.isNotEmpty()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
        ) {

            items(membersDebts) { debt ->
                Card() {
                    Column() {
                        Row {
                            Text(text = "Mdaiwa: ${debt.debtorFirstName} ${debt.debtorLastName}")
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "Date: ${debt.date}")
                        }
                        Text(text = "Maelezo: ${debt.debtDesc}")
                        Row {
                            Text(text = "Kiasi: ${debt.debtAmount} Tsh")
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "Status: ${debt.debtStatus}")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    } else {
        Text(text = "Hakuna madeni yoyote kwa sasa.")
    }
}


