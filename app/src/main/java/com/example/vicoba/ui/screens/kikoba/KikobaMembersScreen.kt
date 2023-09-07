package com.example.vicoba.ui.screens.kikoba

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vicoba.ui.components.DisplayMemberCard
import com.example.vicoba.ui.viewmodels.KikobaViewModel

@Composable
fun KikobaMembersScreen(
    kikobaViewModel: KikobaViewModel,
    modifier: Modifier
) {
    /** Get list of members from the kikoba view model */
    val members = kikobaViewModel.members.value

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "${members.size} Members.", fontSize = 30.sp)

        Spacer(modifier = modifier.height(10.dp))

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
    }
}