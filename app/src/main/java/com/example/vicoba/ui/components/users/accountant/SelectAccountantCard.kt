package com.example.vicoba.ui.components.users.accountant

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.vicoba.R
import com.example.vicoba.data.models.keys.KikobaLeaderID
import com.example.vicoba.data.models.lists.KikobaMember
import com.example.vicoba.ui.viewmodels.KikobaManagementViewModel
import com.example.vicoba.ui.viewmodels.KikobaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAccountantCard(
    members: List<KikobaMember>,
    kikobaManagementViewModel: KikobaManagementViewModel,
    kikobaViewModel: KikobaViewModel
) {
    val activeKikoba = kikobaViewModel.activeKikoba.collectAsState().value
    var selected by remember { mutableStateOf("") }
    var showSaveBtn by remember { mutableStateOf(false) }
    var onSaveBtnClicked by remember { mutableStateOf(false) }
    var memberKey by remember { mutableStateOf(0) }
    var userID by remember { mutableStateOf(0) }

    /** the expanded state of the Text Field */
    var mExpanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(166.dp)
            .shadow(
                elevation = 2.dp
            )
    ) {
        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Mhasibu",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value = selected,
            onValueChange = {},
            modifier = Modifier
                .width(300.dp),
            label = { Text(text = "Chagua mhasibu") },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Person,
                    contentDescription = "contentDescription",
                    modifier = Modifier
                        .clickable { mExpanded = !mExpanded })
            }
        )

        Spacer(modifier = Modifier.height(2.dp))

        if (showSaveBtn) {
            Button(
                onClick = {
                    kikobaManagementViewModel.selectKikobaAccountant(
                        kikobaLeaderID = KikobaLeaderID(
                            kikobaKey = activeKikoba.kikobaID,
                            memberKey = memberKey,
                            userID = userID,
                            kikobaName = activeKikoba.kikobaName
                        )
                    )
                    showSaveBtn = false
                    onSaveBtnClicked = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_color)),
                modifier = Modifier
                    .width(300.dp)
                    .padding(horizontal = 16.dp)
                    .shadow(
                        elevation = 10.dp,
                    )

            ) {
                Text(
                    text = "Select",
                    color = colorResource(id = R.color.black)
                )
            }
            Spacer(modifier = Modifier.width(30.dp))
        }

        if (onSaveBtnClicked) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark_green)),
                modifier = Modifier
                    .width(300.dp)
                    .padding(horizontal = 16.dp)
                    .shadow(
                        elevation = 10.dp,
                    )

            ) {
                Text(
                    text = "Selected",
                    color = colorResource(id = R.color.black)
                )
            }
            Spacer(modifier = Modifier.width(30.dp))
        }


    }

    /** Create a drop-down menu with list of cities,
     * when clicked, set the Text Field text as the city selected
     */
    DropdownMenu(
        expanded = mExpanded,
        onDismissRequest = { mExpanded = false },
        modifier = Modifier
            .width(335.dp)
    ) {
        members.forEach { member ->
            DropdownMenuItem(
                onClick = {
                    selected = "${member.firstName} ${member.lastName}"
                    memberKey = member.memberID
                    userID = member.userID
                    mExpanded = false
                    showSaveBtn = true
                },
                text = {

                    Card(
                        modifier = Modifier
                            .height(50.dp)
                            .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "View",
                                modifier = Modifier.clickable {}
                            )

                            Text(
                                text = "${member.firstName} ${member.lastName}",
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            )
        }
    }
}