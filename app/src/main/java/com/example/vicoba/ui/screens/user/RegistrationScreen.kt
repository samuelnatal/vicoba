package com.example.vicoba.ui.screens.user

import android.app.DatePickerDialog
import android.util.Patterns
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vicoba.R
import com.example.vicoba.data.models.lists.addresses
import com.example.vicoba.data.models.lists.occupations
import com.example.vicoba.ui.components.DatePickerField
import com.example.vicoba.ui.components.DropDownOutlineTextField
import com.example.vicoba.ui.navigation.AppRoutes
import com.example.vicoba.ui.viewmodels.RegistrationViewModel
import com.example.vicoba.ui.viewmodels.SessionViewModel
import java.util.Calendar
import java.util.Date

/** Composable that provides registration UI to the user*/
@Composable
fun RegisterScreen(
    sessionViewModel: SessionViewModel,
    regViewModel: RegistrationViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    /** A userUiState variable observes the state of NewKikobaUser data class*/
    val userUiState by regViewModel.newKikobaUserState.collectAsState()

    /**
     * Map a list of occupation object into into a list of individual strings
     * representing occupation ID and its title to be displayed in a drop down list.
     */
    val occupationList = occupations.map { occupation ->
        "${occupation.occupationID} . ${occupation.occupationName}"
    }

    /**
     * Map a list of occupation object into into a list of individual strings
     * representing occupation ID and its title to be displayed in a drop down list.
     */
    val addressList = addresses.map { address ->
        "${address.addressID} . ${address.region} ${address.district} ${address.ward}"
    }

    /** Retype password observable */
    var repwd by remember { mutableStateOf("") }

    /** Errors observables to update the appearance of the data field accordingly
     * based on the validity of data passed in
     */
    var fnameFieldError by remember { mutableStateOf(false) }
    var lnameFieldError by remember { mutableStateOf(false) }
    var phoneFieldError by remember { mutableStateOf(false) }
    var emailFieldError by remember { mutableStateOf(false) }
    var occupationFieldError by remember { mutableStateOf(false) }
    var addressFieldError by remember { mutableStateOf(false) }
    var pwdFieldError by remember { mutableStateOf(false) }
    var repwdFieldError by remember { mutableStateOf(false) }


    /** Observebles that display the status of the error below a particular field
     * when their is an error in it for better user experience
     */
    var fnameStatus by remember { mutableStateOf("") }
    var lnameStatus by remember { mutableStateOf("") }
    var phoneStatus by remember { mutableStateOf("") }
    var emailStatus by remember { mutableStateOf("") }
    var occupationStatus by remember { mutableStateOf("") }
    var addressStatus by remember { mutableStateOf("") }
    var genderStatus by remember { mutableStateOf("") }
    var pwdStatus by remember { mutableStateOf("") }
    var repwdStatus by remember { mutableStateOf("") }

    /** Fetching the Local Context */
    val mContext = LocalContext.current

    /** Declaring integer valuesfor year, month and day */
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    /** Initializing a Calendar */
    val mCalendar = Calendar.getInstance()

    /** Fetching current year, month and day */
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    /**Declaring a string value to store date in string format */
    val dob = remember { mutableStateOf("$mYear-${mMonth + 1}-$mDay") }

    /** Declaring DatePickerDialog and setting initial values as current values (present year, month and day) */
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, Year: Int, Month: Int, mDayOfMonth: Int ->
            dob.value = "$Year-${Month + 1}-$mDayOfMonth"
        }, mYear, mMonth, mDay
    )

    regViewModel.updateDob(dob.value)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.light_grayish_blue))
    ) {
        Column() {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .background(
                        colorResource(id = R.color.white),
                        shape = RoundedCornerShape(10.dp)
                    )

            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround

                ) {
                    RegistrationHeader()
                    RegistrationBody(
                        fname = userUiState.firstName,
                        fnameStatus = fnameStatus,
                        fnameFieldError = fnameFieldError,
                        onFnameChange = { regViewModel.updateFirstName(it) },
                        lname = userUiState.lastName,
                        lnameStatus = lnameStatus,
                        lnameFieldError = lnameFieldError,
                        onLnameChange = { regViewModel.updateLastName(it) },
                        email = userUiState.email,
                        emailStatus = emailStatus,
                        emailFieldError = emailFieldError,
                        onEmailChange = { regViewModel.updateEmail(it) },
                        address = userUiState.address,
                        addressStatus = addressStatus,
                        addressFieldError = addressFieldError,
                        addressList = addressList,
                        onAddressChange = { regViewModel.updateAddress(it) },
                        gender = userUiState.gender,
                        genderStatus = genderStatus,
                        onMaleGenderSelect = { regViewModel.updateGender("m") },
                        onFemaleGenderSelect = { regViewModel.updateGender("f") },
                        occupation = userUiState.occupation,
                        occupationStatus = occupationStatus,
                        occupationFieldError = occupationFieldError,
                        occupationList = occupationList,
                        onOccupationChange = { regViewModel.updateOccupation(it) },
                        phone = userUiState.phone,
                        phoneStatus = phoneStatus,
                        phoneFieldError = phoneFieldError,
                        onPhoneChange = { regViewModel.updatePhone(it) },
                        pwd = userUiState.pwd,
                        pwdStatus = pwdStatus,
                        pwdFieldError = pwdFieldError,
                        onPwdChange = { regViewModel.updatePwd(it) },
                        repwd = repwd,
                        repwdStatus = repwdStatus,
                        repwdFieldError = repwdFieldError,
                        onrepwdChange = { repwd = it },
                        dob = dob.value,
                        pickdate = { mDatePickerDialog.show() },
                    )
                    RegistrationFooter(
                        onRegister = {

                            /** A block that perform datavalidation passing in registration form UI by the user */

                            if (userUiState.firstName.isEmpty()) {
                                fnameFieldError = true
                                fnameStatus = "*Jaza jina lako la kwanza."
                            } else if (userUiState.lastName.isEmpty()) {
                                fnameFieldError = false
                                fnameStatus = ""
                                lnameFieldError = true
                                lnameStatus = "*Jaza jina lako la mwisho."
                            } else if (userUiState.phone.isEmpty()) {
                                lnameFieldError = false
                                lnameStatus = ""
                                phoneFieldError = true
                                phoneStatus = "*Jaza nambari ya simu."
                            } else if (userUiState.phone.length != 10) {
                                phoneStatus = "*Nambari za simu zinapaswa kuwa 10."
                            } else if (userUiState.email.isEmpty()) {
                                phoneFieldError = false
                                phoneStatus = ""
                                emailFieldError = true
                                emailStatus = "*Jaza barua pepe."
                            } else if (!Patterns.EMAIL_ADDRESS.matcher(userUiState.email)
                                    .matches()
                            ) {
                                emailStatus = "*Barua pepe sio sahihi."
                            } else if (userUiState.occupation.isEmpty()) {
                                emailFieldError = false
                                emailStatus = ""
                                occupationFieldError = true
                                occupationStatus = "*Jaza kazi uliyo nayo kwa sasa."
                            } else if (userUiState.address.isEmpty()) {
                                occupationFieldError = false
                                occupationStatus = ""
                                addressFieldError = true
                                addressStatus = "*Jaza mahali unakoishi kwa sasa."
                            } else if (userUiState.gender.isEmpty()) {
                                addressFieldError = false
                                addressStatus = ""
                                genderStatus = "*Jaza jinsia yako."
                            } else if (userUiState.pwd.isEmpty()) {
                                genderStatus = ""
                                pwdFieldError = true
                                pwdStatus = "*Jaza nywila utakayokua unatumia."
                            } else if (userUiState.pwd.length < 8) {
                                pwdStatus = "*Nywila inapaswa kuwa si chini ya tarakimu nane"
                            } else if (repwd.isEmpty()) {
                                pwdFieldError = false
                                pwdStatus = ""
                                repwdFieldError = true
                                repwdStatus = "*Jaza nywila uliyoweka hapo juu."
                            } else if (!(userUiState.pwd == repwd)) {
                                repwdStatus = "*Nywila iliyoingiza sahihi."
                            } else {
                                regViewModel.registerNewUser(userUiState)
                                sessionViewModel.signIn()
                                navHostController.navigate(AppRoutes.Home.name)
                            }
                        }
                    )
                }
            }
        }

    }

}


/** Registration header */
@Composable
fun RegistrationHeader() {

    Text(
        text = stringResource(id = R.string.member_signup_text),
        style = MaterialTheme.typography.headlineSmall,
        color = colorResource(id = R.color.greenish_teal)
    )
}

/** registration body */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationBody(
    fname: String,
    fnameStatus: String,
    fnameFieldError: Boolean,
    onFnameChange: (String) -> Unit,
    lname: String,
    lnameStatus: String,
    lnameFieldError: Boolean,
    onLnameChange: (String) -> Unit,
    phone: String,
    phoneStatus: String,
    phoneFieldError: Boolean,
    onPhoneChange: (String) -> Unit,
    email: String,
    emailStatus: String,
    emailFieldError: Boolean,
    onEmailChange: (String) -> Unit,
    occupation: String,
    occupationStatus: String,
    occupationFieldError: Boolean,
    occupationList: List<String>,
    onOccupationChange: (String) -> Unit,
    address: String,
    addressStatus: String,
    addressFieldError: Boolean,
    addressList: List<String>,
    onAddressChange: (String) -> Unit,
    gender: String,
    genderStatus: String,
    onMaleGenderSelect: () -> Unit,
    onFemaleGenderSelect: () -> Unit,
    pwd: String,
    pwdStatus: String,
    pwdFieldError: Boolean,
    onPwdChange: (String) -> Unit,
    repwd: String,
    repwdStatus: String,
    repwdFieldError: Boolean,
    onrepwdChange: (String) -> Unit,
    dob: String,
    pickdate: () -> Unit,

    ) {
    Column() {

        Spacer(modifier = Modifier.height(16.dp))
        Column {
            OutlinedTextField(
                value = fname,
                onValueChange = onFnameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Jina la kwanza") },
                singleLine = true,
                isError = fnameFieldError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
            )
            Text(
                text = fnameStatus,
                color = Color.Red,
                fontSize = 15.sp
            )

            OutlinedTextField(
                value = lname,
                onValueChange = onLnameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Jina la mwisho") },
                singleLine = true,
                isError = lnameFieldError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
            )
            Text(
                text = lnameStatus,
                color = Color.Red,
                fontSize = 15.sp
            )

            OutlinedTextField(
                value = phone,
                onValueChange = onPhoneChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Nambari ya simu") },
                singleLine = true,
                isError = phoneFieldError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
            )
            Text(
                text = phoneStatus,
                color = Color.Red,
                fontSize = 15.sp
            )

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Barua pepe") },
                singleLine = true,
                isError = emailFieldError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
            )
            Text(
                text = emailStatus,
                color = Color.Red,
                fontSize = 15.sp
            )

            DropDownOutlineTextField(
                value = occupation,
                dropDownList = occupationList,
                label = "Kazi",
                onValueChange = onOccupationChange,
                isError = occupationFieldError,
            )
            Text(
                text = occupationStatus,
                color = Color.Red,
                fontSize = 15.sp
            )

            DropDownOutlineTextField(
                value = address,
                dropDownList = addressList,
                label = "Mahali unakoishi",
                onValueChange = onAddressChange,
                isError = addressFieldError,
            )
            Text(
                text = addressStatus,
                color = Color.Red,
                fontSize = 15.sp
            )

            GenderSelector(
                gender = gender,
                onMaleGenderSelect = onMaleGenderSelect,
                onFemaleGenderSelect = onFemaleGenderSelect
            )
            Text(
                text = genderStatus,
                color = Color.Red,
                fontSize = 15.sp
            )

            DatePickerField(
                dob = dob,
                pickdate = pickdate
            )

            OutlinedTextField(
                value = pwd,
                onValueChange = onPwdChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                isError = pwdFieldError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
            )
            Text(
                text = pwdStatus,
                color = Color.Red,
                fontSize = 15.sp
            )

            OutlinedTextField(
                value = repwd,
                onValueChange = onrepwdChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Retype password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                isError = repwdFieldError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
            )
            Text(
                text = repwdStatus,
                color = Color.Red,
                fontSize = 15.sp
            )

            //Forgot password button
            TextButton(onClick = {}, modifier = Modifier.align(Alignment.End)) {
                /*TODO*/
                Text(
                    text = stringResource(id = R.string.forget_password_text),
                    color = colorResource(id = R.color.greenish_teal)
                )

            }
        }
    }

}

/** registration footer compose */
@Composable
fun RegistrationFooter(
    onRegister: () -> Unit
) {

    Column(
        Modifier
            .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = onRegister,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = MaterialTheme.shapes.medium,
                )


        )
        {
            Text(text = stringResource(id = R.string.registration_text))
        }

        TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(id = R.string.dont_have_account_text),
                color = colorResource(id = R.color.greenish_teal)
            )
        }
    }
}

/** gender selector compose */
@Composable
fun GenderSelector(
    gender: String,
    onMaleGenderSelect: () -> Unit,
    onFemaleGenderSelect: () -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    Column {
        Text(text = "Jinsia")
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = gender == "m",
                onClick = onMaleGenderSelect
            )

            Text(text = "Mme")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = gender == "f",
                onClick = onFemaleGenderSelect
            )

            Text(text = "Mke")
        }

    }
}

