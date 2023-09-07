package com.example.vicoba.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vicoba.R
import com.example.vicoba.data.models.keys.KikobaIDUserID
import com.example.vicoba.data.models.lists.KikobaMember
import com.example.vicoba.ui.components.popups.ActionAlertDialog
import com.example.vicoba.ui.viewmodels.KikobaManagementViewModel
import com.example.vicoba.ui.viewmodels.KikobaViewModel

@Composable
fun RemoveMemberCard(
    member:KikobaMember,
    kikobaManagementViewModel: KikobaManagementViewModel,
    kikobaViewModel: KikobaViewModel,
){
    Card(
        modifier = Modifier.height(50.dp)
            .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
    ) {

        var onRemoveClicked by remember { mutableStateOf(false) }
        var removeActionPerformed by remember { mutableStateOf(false) }

        /** activeKikoba variable stores information of the current active kikoba */
        val activeKikoba = kikobaViewModel.activeKikoba.collectAsState()


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.members),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Text(
                text = "${member.firstName} ${member.lastName}",
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
            if(!removeActionPerformed){
                Image(
                    painter = painterResource(R.drawable.remove_person),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                        .clickable {
                            onRemoveClicked = true
                        }
                )
            }else{
                Text(text="Removed", color = Color.Red)
            }


            ActionAlertDialog(
                title = "Remove member.",
                body = "Do you real want to remove this member to your kikoba?",
                showDialog = onRemoveClicked,
                onAccept = {
                    removeActionPerformed = true

                    kikobaManagementViewModel.removeKikobaMember(
                        kikobaIDUserID = KikobaIDUserID(
                            userID = member.memberID,
                            kikobaID = activeKikoba.value.kikobaID
                        )
                    )
                    onRemoveClicked = false
                },
                onDismiss = { showDialogStatus ->
                    onRemoveClicked = showDialogStatus
                }

            )
        }
    }
}
