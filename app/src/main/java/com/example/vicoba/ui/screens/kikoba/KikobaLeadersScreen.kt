package com.example.vicoba.ui.screens.kikoba

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.vicoba.R
import com.example.vicoba.ui.viewmodels.KikobaViewModel

@Composable
fun KikobaLeadersScreen(
    kikobaViewModel: KikobaViewModel
) {
    /** activeKikoba variable stores information of the current active kikoba */
    val activeKikoba = kikobaViewModel.activeKikoba.collectAsState().value

    val leaders = listOf(
        Pair(first = "Mwenyekiti", second = activeKikoba.kikobaChairPersonName),
        Pair(first = "Mhasibu", second = activeKikoba.kikobaAccountantName),
        Pair(first = "Katibu", second = activeKikoba.kikobaSecretaryName)
    )

    Column {
        leaders.forEach { leader ->
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = leader.first,
                    style = MaterialTheme.typography.headlineSmall,
                    color = colorResource(id = R.color.greenish_teal)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = leader.second,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }
    }
}