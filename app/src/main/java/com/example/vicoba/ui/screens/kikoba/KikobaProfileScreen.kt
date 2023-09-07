package com.example.vicoba.ui.screens.kikoba

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vicoba.R
import com.example.vicoba.data.models.lists.addresses
import com.example.vicoba.ui.viewmodels.KikobaManagementViewModel
import com.example.vicoba.ui.viewmodels.KikobaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KikobaProfileScreen(
    kikobaViewModel: KikobaViewModel,
    kikobaManagementViewModel: KikobaManagementViewModel
) {
    /** activeKikoba variable stores information of the current active kikoba */
    val activeKikoba = kikobaViewModel.activeKikoba.collectAsState().value

    val scrollState = rememberScrollState()

    /** object state representing newly updated kikoba information */
    val editedKikoba = kikobaManagementViewModel.editedKikobaProfile.collectAsState().value

    /** A list of address to be displayed to the user when selecting location of the kikoba */
    val addressList = addresses.map { address ->
        "${address.addressID} . ${address.region} ${address.district} ${address.ward}"
    }

    /** set kikobaID for the current updated kikoba */
    kikobaManagementViewModel.setEditedKikobaID(activeKikoba.kikobaID)

    /** A LIST OF VARIABLES BELOW ARE FOR THE LOCATION PICKER WHEN UPDATING KIKOBA LOCATION */
    /** the expanded state of the Text Field */
    var mExpanded by remember { mutableStateOf(false) }
    val isError by remember { mutableStateOf(false) }

    var onSaveBtnClicked by remember { mutableStateOf(false) }

    /** Up Icon when expanded and down icon when collapsed */
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(text = "Jina la kikoba.", fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = editedKikoba.kikobaName,
            onValueChange = { kikobaManagementViewModel.updateKikobaName(it) },
            label = { Text(activeKikoba.kikobaName) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit kikoba name",
                    tint = colorResource(id = R.color.greenish_teal),
                    modifier = Modifier.size(30.dp)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Weka maelezo au sheria za kikundi ama kikoba.", fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = editedKikoba.kikobaDesc,
            onValueChange = { kikobaManagementViewModel.updateKikobaDesc(it) },
            label = { Text(activeKikoba.kikobaDesc) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit kikoba description",
                    tint = colorResource(id = R.color.greenish_teal),
                    modifier = Modifier.size(30.dp)
                )
            },
            singleLine = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Mahali.", fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = editedKikoba.kikobaLocationID,
            onValueChange = { kikobaManagementViewModel.updateKikobaLocation(it) },
            isError = isError,
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("${activeKikoba.kikobaRegion}, ${activeKikoba.kikobaDistrict}, ${activeKikoba.kikobaWard}") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Thamani ya hisa.", fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = editedKikoba.sharePrice,
            onValueChange = { kikobaManagementViewModel.updateSharePrice(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "${activeKikoba.sharePrice}") },
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit share price",
                    tint = colorResource(id = R.color.greenish_teal),
                    modifier = Modifier.size(30.dp)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Kiwango cha juu cha kununua hisa.", fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = editedKikoba.maxShare,
            onValueChange = { kikobaManagementViewModel.updateMaxShare(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "${activeKikoba.maxShare}") },
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit maxmum share amount",
                    tint = colorResource(id = R.color.greenish_teal),
                    modifier = Modifier.size(30.dp)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Mzunguko wa kuchangia.", fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = editedKikoba.shareCircle,
            onValueChange = { kikobaManagementViewModel.updateShareCircle(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "${activeKikoba.shareCircle}") },
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit share circle",
                    tint = colorResource(id = R.color.greenish_teal),
                    modifier = Modifier.size(30.dp)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
        )


        /** Create a drop-down menu with list of cities,
         * when clicked, set the Text Field text as the city selected
         */
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(335.dp)
        ) {
            addressList.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        kikobaManagementViewModel.updateKikobaLocation(label)
                        mExpanded = false
                    },
                    text = {
                        Text(text = label)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (!onSaveBtnClicked) {
            Button(
                onClick = {
                    onSaveBtnClicked = true
                    kikobaManagementViewModel.updateKikobaProfile(editedKikoba)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.button_color)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = MaterialTheme.shapes.medium,
                    )

            ) {
                Text(text = "Hifadhi", color = colorResource(id = R.color.black))
            }
        } else {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.dark_green)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = MaterialTheme.shapes.medium,
                    )

            ) {
                Text(text = "Mabadiliko yamehifadhiwa", color = colorResource(id = R.color.black))
            }
        }

    }
}