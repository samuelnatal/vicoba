package com.example.vicoba.ui.screens.member

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vicoba.R
import com.example.vicoba.ui.components.DropDownOutlineTextField
import com.example.vicoba.ui.viewmodels.KikobaViewModel
import com.example.vicoba.ui.viewmodels.LoanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestLoanScreen(
    loanViewModel: LoanViewModel,
    kikobaViewModel: KikobaViewModel,
) {

    /** A userUiState variable observes the state of loan form data for loan request */
    val loanFormState by loanViewModel.loanFormState.collectAsState()

    val memberID = kikobaViewModel.kikobaMemberID.collectAsState().value
    loanViewModel.setKikobaMemberID(memberID)

    /** activeKikoba variable stores information of the current active kikoba */
    val activeKikoba = kikobaViewModel.activeKikoba.collectAsState().value
    loanViewModel.setKikobaKey(activeKikoba.kikobaID)

    /** Get list of members from the kikoba view model */
    val members = kikobaViewModel.members.value

    /** Transform member list into string to be displayed in a drop down list */
    val membersList = members.map { member ->
        "${member.memberID}. ${member.firstName} ${member.lastName} "
    }

    val scrollState = rememberScrollState()
    var onSubmitBtnClicked by remember { mutableStateOf(false) }

    /** Errors observables to update the appearance of loan form UI state accordingly
     * based on the validity of data passed in
     */
    var kiasiFieldError by remember { mutableStateOf(false) }
    var maelezoFieldError by remember { mutableStateOf(false) }
    var dhamanaFieldError by remember { mutableStateOf(false) }
    var mdhamini1FieldError by remember { mutableStateOf(false) }
    var mdhamini2FieldError by remember { mutableStateOf(false) }
    var mdhamini3FieldError by remember { mutableStateOf(false) }

    /** Errors observables to update the error status of loan form UI state accordingly
     * based on the validity of data passed in
     */
    var kiasiErrorStatus by remember { mutableStateOf("") }
    var maelezoErrorStatus by remember { mutableStateOf("") }
    var dhamanaErrorStatus by remember { mutableStateOf("") }
    var mdhamini1ErrorStatus by remember { mutableStateOf("") }
    var mdhamini2ErrorStatus by remember { mutableStateOf("") }
    var mdhamini3ErrorStatus by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = loanFormState.loanAmount,
            onValueChange = { loanViewModel.updateLoanAmount(it) },
            isError = kiasiFieldError,
            label = { Text("Kiasi cha mkopo") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = kiasiErrorStatus,
            color = Color.Red,
            fontSize = 15.sp
        )


        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = loanFormState.loanDesc,
            onValueChange = { loanViewModel.updateLoanDesc(it) },
            isError = maelezoFieldError,
            label = { Text("Maelezo mafupi kuhusu mkopo.") },
            singleLine = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
        )
        Text(
            text = maelezoErrorStatus,
            color = Color.Red,
            fontSize = 15.sp
        )


        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = loanFormState.guarantee,
            onValueChange = { loanViewModel.updateGuarantee(it) },
            isError = dhamanaFieldError,
            label = { Text("Dhamana ya mkopo") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = dhamanaErrorStatus,
            color = Color.Red,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        DropDownOutlineTextField(
            value = loanFormState.guarantor1ID,
            dropDownList = membersList,
            isError = mdhamini1FieldError,
            label = "Chagua mdhamini wako wa kwanza",
            onValueChange = { loanViewModel.updateGuarantor1ID(it) },
        )
        Text(
            text = mdhamini1ErrorStatus,
            color = Color.Red,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        DropDownOutlineTextField(
            value = loanFormState.guarantor2ID,
            dropDownList = membersList,
            isError = mdhamini2FieldError,
            label = "Chagua mdhamini wako wa pili",
            onValueChange = { loanViewModel.updateGuarantor2ID(it) },
        )
        Text(
            text = mdhamini2ErrorStatus,
            color = Color.Red,
            fontSize = 15.sp
        )


        Spacer(modifier = Modifier.height(20.dp))

        DropDownOutlineTextField(
            value = loanFormState.guarantor3ID,
            dropDownList = membersList,
            isError = mdhamini3FieldError,
            label = "Chagua mdhamini wa tatu",
            onValueChange = { loanViewModel.updateGuarantor3ID(it) },
        )
        Text(
            text = mdhamini3ErrorStatus,
            color = Color.Red,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (!onSubmitBtnClicked) {
            Button(
                onClick = {
                    /** A block that perform datavalidation passing in registration form UI by the user */

                    if (loanFormState.loanAmount.isEmpty()) {
                        kiasiFieldError = true
                        kiasiErrorStatus = "*Weka kiasi cha mkopo unachoomba."
                    } else if (loanFormState.loanDesc.isEmpty()) {
                        kiasiFieldError = false
                        kiasiErrorStatus = ""
                        maelezoFieldError = true
                        maelezoErrorStatus = "*Ambatanisha maelezo mafupi ya ombi lako."
                    } else if (loanFormState.guarantee.isEmpty()) {
                        maelezoFieldError = false
                        maelezoErrorStatus = ""
                        dhamanaFieldError = true
                        dhamanaErrorStatus = "*Weka dhamana ya mkopo."
                    } else if (loanFormState.guarantor1ID.isEmpty()) {
                        dhamanaFieldError = false
                        dhamanaErrorStatus = ""
                        mdhamini1FieldError = true
                        mdhamini1ErrorStatus = "*Jaza jina kamili la mdhamini wako."
                    } else if (loanFormState.guarantor2ID.isEmpty()) {
                        mdhamini1FieldError = false
                        mdhamini1ErrorStatus = ""
                        mdhamini2FieldError = true
                        mdhamini2ErrorStatus = "*Jaza namba ya simu ya mdhamini wako.."
                    }else if (loanFormState.guarantor3ID.isEmpty()) {
                        mdhamini2FieldError = false
                        mdhamini2ErrorStatus = ""
                        mdhamini3FieldError = true
                        mdhamini3ErrorStatus = "*Jaza kazi ya mdhamini wako."
                    } else {
                        loanViewModel.requestLoan(loanFormState)
                        onSubmitBtnClicked = true
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.button_color)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
                    .shadow(
                        elevation = 10.dp,
                        shape = MaterialTheme.shapes.medium,
                    )

            ) {
                Text(text = "Tuma ombi", color = colorResource(id = R.color.black))
            }
        } else {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.dark_green)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
                    .shadow(
                        elevation = 10.dp,
                        shape = MaterialTheme.shapes.medium,
                    )

            ) {
                Text(text = "Ombi limetumwa", color = colorResource(id = R.color.black))
            }
        }

    }
}