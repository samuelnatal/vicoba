package com.example.vicoba.ui.screens.member

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.vicoba.R
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.viewmodels.KikobaViewModel
import com.example.vicoba.ui.viewmodels.ShareViewModel

@Composable
fun MySharesScreen(
    shareViewModel: ShareViewModel,
    kikobaViewModel: KikobaViewModel
) {
    /** activeKikoba variable stores information of the current active kikoba */
    val activeKikoba = kikobaViewModel.activeKikoba.collectAsState().value

    val membersShares = shareViewModel.membersShares.value

    /** user variable holds the information of the active user */
    val user = KikobaUserState.userState.collectAsState().value.user

    /** Set column size of the table. */
    val column1Weight = .3f // 30%
    val column2Weight = .2f // 70%
    val column3Weight = .3f // 70%
    val column4Weight = .3f // 70%

    if (membersShares.isNotEmpty()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
        ) {
            /** Table header */
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Row(Modifier.background(colorResource(id = R.color.greenish_teal))) {
                    TableCell(text = "Name", weight = column1Weight)
                    TableCell(text = "Hisa", weight = column2Weight)
                    TableCell(text = "Thamani", weight = column3Weight)
                    TableCell(text = "Tarehe", weight = column4Weight)
                }
            }
            /** Table body */

            items(membersShares) { share ->
                if(share.ownerID == user.userID) Row(
                    Modifier.fillMaxWidth()
                ) {
                    TableCell(
                        text = "${share.ownerFirstName} ${share.ownerLastName}",
                        weight = column1Weight
                    )
                    TableCell(text = "${share.shareAmount}", weight = column2Weight)
                    TableCell(
                        text = "${share.shareAmount * activeKikoba.sharePrice}",
                        weight = column3Weight
                    )
                    TableCell(text = "${share.date}", weight = column4Weight)
                }
            }
        }
    } else {
        Text(text = "Hakuna mikopo yoyote kwa sasa.")
    }
}


@Composable
fun RowScope.MyShareTableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}
