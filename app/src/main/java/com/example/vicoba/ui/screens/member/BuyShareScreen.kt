package com.example.vicoba.ui.screens.member

import OkAlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vicoba.R
import com.example.vicoba.data.models.items.ShareCoupon
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.viewmodels.KikobaViewModel
import com.example.vicoba.ui.viewmodels.ShareViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyShareScreen(
    shareViewModel: ShareViewModel,
    kikobaViewModel: KikobaViewModel,
) {
    /** A userUiState variable observes the state of loan form data for loan request */
    val shareFormState by shareViewModel.shareFormState.collectAsState()

    /** activeKikoba variable stores information of the current active kikoba */
    val activeKikoba = kikobaViewModel.activeKikoba.collectAsState().value

    /** user variable holds the information of the active user */
    val user = KikobaUserState.userState.collectAsState().value.user

    /** Get list of members from the kikoba view model */
    val members = kikobaViewModel.members.value
    val activeMember = members.find { it.userID == user.userID }

    var onBuyBtnClicked by remember { mutableStateOf(false) }

    /** Errors observables to update the appearance of loan form UI state accordingly
     * based on the validity of data passed in
     */
    var shareAmountFieldError by remember { mutableStateOf(false) }

    /** Errors observables to update the error status of loan form UI state accordingly
     * based on the validity of data passed in
     */
    var shareAmountErrorStatus by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        ) {
        Card(
            modifier = Modifier
                .height(500.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
            ) {
                Text(
                    text = "Hisa 1 = ${activeKikoba.sharePrice} Tsh ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Kiwango cha juu cha ununuzi wa hisa =  ${activeKikoba.maxShare} ",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = shareFormState.shareAmount,
                    onValueChange = { shareViewModel.updateShareAmount(it) },
                    isError = shareAmountFieldError,
                    label = { Text("Idadi ya hisa") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = shareAmountErrorStatus,
                    color = Color.Red,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (!onBuyBtnClicked) {
                    Button(
                        onClick = {
                            /** A block that perform datavalidation passing in registration form UI by the user */

                            if (shareFormState.shareAmount.isEmpty()) {
                                shareAmountFieldError = true
                                shareAmountErrorStatus = "*Weka idadi ya hisa unazotaka kununua."
                            } else if (shareFormState.shareAmount.toInt() > activeKikoba.maxShare) {
                                shareAmountErrorStatus =
                                    "*Umedhidisha kiwango cha juu cha ununuzi wa hisa."
                            } else {
                                shareAmountErrorStatus = ""
                                val updatedWallet =
                                    activeKikoba.kikobaWallet + (shareFormState.shareAmount.toInt() * activeKikoba.sharePrice)
                                if (activeMember != null) {
                                    shareViewModel.buyShare(
                                        shareCoupon = ShareCoupon(
                                            userID = user.userID,
                                            memberID = activeMember.memberID,
                                            kikobaID = activeKikoba.kikobaID,
                                            kikobaName = activeKikoba.kikobaName,
                                            updatedWallet = updatedWallet,
                                            shareAmount = shareFormState.shareAmount
                                        )
                                    )
                                }
                                onBuyBtnClicked = true
                                showDialog = true
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
                        Text(text = "Nunua hisa", color = colorResource(id = R.color.black))
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
                        Text(
                            text = "Malipo yamekamilika.",
                            color = colorResource(id = R.color.black)
                        )
                    }
                }
               if (showDialog)
                OkAlertDialog(
                    icon = Icons.Default.CheckCircle,
                    title = "Manunuzi ya hisa yamekamilika.",
                    body = "Umefanikiwa kununua hisa ${shareFormState.shareAmount} zenye thamani ya shilingi ${shareFormState.shareAmount.toInt() * activeKikoba.sharePrice}.",
                    showDialog = showDialog,
                    onDismiss = { showDialogStatus ->
                        showDialog = showDialogStatus
                    }
                )
            }
        }
    }
}