package com.example.vicoba.ui.screens.kikoba

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vicoba.R
import com.example.vicoba.data.models.lists.addresses
import com.example.vicoba.ui.components.DropDownOutlineTextField
import com.example.vicoba.ui.navigation.AppRoutes
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.viewmodels.KikobaViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateKicobaScreen(
    kikobaViewModel: KikobaViewModel,
    navController: NavHostController,
    modifier: Modifier
) {

    /** Retrieve the ID of the active user */
    val user = KikobaUserState.userState.collectAsState()

    /** A list of address to be displayed to the user when selecting location of the kikoba */
    val addressList = addresses.map { address ->
        "${address.addressID} . ${address.region} ${address.district} ${address.ward}"
    }

    /** Observe the information prepared to add new kikoba into the app */
    val newKikoba by kikobaViewModel.kikobaToAdd.collectAsState()

    /** Observable to provide the details of each specific field errors */
    var nameStatus by remember { mutableStateOf("") }
    var locationStatus by remember { mutableStateOf("") }
    var descriptionStatus by remember { mutableStateOf("") }

    /** Observable to highlight the field lines red once their is an error */
    var nameFieldError by remember { mutableStateOf(false) }
    var locationFieldError by remember { mutableStateOf(false) }
    var descriptionFieldError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)

    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Card(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.light_grayish_blue),
                    shape = RoundedCornerShape(10.dp)
                )
                .height(550.dp)


        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                FormBody(
                    name = newKikoba.kikobaName,
                    nameStatus = nameStatus,
                    nameFieldError = nameFieldError,
                    onNameChange = { kikobaViewModel.updateKikobaName(it) },
                    address = newKikoba.kikobaLocationID,
                    addressFieldError = locationFieldError,
                    addressList = addressList,
                    addressStatus = locationStatus,
                    onAddressChange = {
                        kikobaViewModel.updateKikobaLocation(it)
                        kikobaViewModel.setKikobaOwner(user.value.user.userID)
                    },
                    description = newKikoba.kikobaDesc,
                    descriptionStatus = descriptionStatus,
                    descriptionFieldError = descriptionFieldError,
                    onDescriptionChange = { kikobaViewModel.updateKikobaDesc(it) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        /** Block of code to perform new kikoba data validation*/
                        /** Block of code to perform new kikoba data validation*/
                        if (newKikoba.kikobaName.isEmpty()) {
                            nameFieldError = true
                            nameStatus = "*Jaza jina la kikoba."
                        } else if (newKikoba.kikobaLocationID.isEmpty()) {
                            nameStatus = ""
                            nameFieldError = false
                            locationFieldError = true
                            locationStatus = "*Jaza sehemu kinakopatikana kikoba."
                        } else if (newKikoba.kikobaDesc.isEmpty()) {
                            locationStatus = ""
                            locationFieldError = false
                            descriptionFieldError = true
                            descriptionStatus = "*Jaza taarifa fupi kuhusiana na kikoba chako."
                        } else {
                            kikobaViewModel.saveNewKikoba(newKikoba)
                            navController.navigate(AppRoutes.Dashboard.name)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 4.dp,
                            shape = MaterialTheme.shapes.medium,
                        )
                )
                {
                    Text(text = stringResource(id = R.string.create_text))
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormBody(
    name: String,
    nameStatus: String,
    nameFieldError: Boolean,
    onNameChange: (String) -> Unit,
    address: String,
    addressStatus: String,
    addressList: List<String>,
    addressFieldError: Boolean,
    onAddressChange: (String) -> Unit,
    description: String,
    descriptionFieldError: Boolean,
    descriptionStatus: String,
    onDescriptionChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .height(450.dp)
    ) {

        Text(
            text = stringResource(id = R.string.group_creation_text),
            style = MaterialTheme.typography.headlineSmall,
            color = colorResource(id = R.color.greenish_teal)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.group_information_text),
            style = MaterialTheme.typography.labelLarge,
            color = colorResource(id = R.color.greenish_teal)
        )

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            isError = nameFieldError,
            label = { Text(text = "Jina la kikoba") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
        )

        Text(
            text = nameStatus,
            color = Color.Red,
            fontSize = 15.sp
        )


        DropDownOutlineTextField(
            value = address,
            dropDownList = addressList,
            label = "Mahali",
            onValueChange = onAddressChange,
            isError = addressFieldError,
        )
        Text(
            text = addressStatus,
            color = Color.Red,
            fontSize = 15.sp
        )

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            modifier = Modifier.fillMaxWidth().height(350.dp),
            label = { Text(text = "Weka maelezo au sheria za kikundi.") },
            isError = descriptionFieldError,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
            maxLines = 10,
            singleLine = false
        )

        Text(
            text = descriptionStatus,
            color = Color.Red,
            fontSize = 15.sp
        )

    }
}


