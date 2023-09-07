package com.example.vicoba.ui.screens.user

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vicoba.R
import com.example.vicoba.ui.navigation.AppRoutes
import com.example.vicoba.ui.viewmodels.LoginViewModel
import com.example.vicoba.ui.viewmodels.SessionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Composable that displays the list of items as [RadioButton] options,
 * [onSelectionChanged] lambda that notifies the parent composable when a new value is selected,
 * [onCancelButtonClicked] lambda that cancels the order when user clicks cancel and
 * [onNextButtonClicked] lambda that triggers the navigation to next screen
 */

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    sessionViewModel: SessionViewModel,
    onSignUpButtonClicked: () -> Unit,
    modifier: Modifier
) {
    /** The state that is tracking UserLoginCredential data class to prepare user for authentication*/
    val userUiState by loginViewModel.userToLogin.collectAsState()

    /** Return coroutine bound to this composable scope*/
    val coroutineScope = rememberCoroutineScope()

    /** A variable that observes the authentication status */
    val authenticated by loginViewModel.authenticated.collectAsState()

    /** A state that maintain whether to display a toast with an error message when failed to login*/
    var displayToast by remember { mutableStateOf(false) }


    /** Observer states that observe and update fields status and appearance
     * accordingly when their is any error on the passed data*/
    var emailStatus by remember { mutableStateOf("") }
    var emailFieldError by remember { mutableStateOf(false) }
    var pwdStatus by remember { mutableStateOf("") }
    var pwdFieldError by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.light_grayish_blue))

    ) {
        Column() {
            Box(
                modifier = Modifier
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
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround

                ) {
                    LoginHeader()
                    LoginFields(
                        email = userUiState.email,
                        emailStatus = emailStatus,
                        emailFieldError = emailFieldError,
                        onEmailChange = { loginViewModel.updateEmail(it) },
                        pwd = userUiState.pwd,
                        pwdStatus = pwdStatus,
                        pwdFieldError = pwdFieldError,
                        onPwdChange = { loginViewModel.updatePwd(it) }

                    )
                    LoginFooter(
                        onSignInClick = {

                            /** Block of code to perform login credential data validation*/

                            /** Block of code to perform login credential data validation*/

                            if (userUiState.email.isEmpty()) {
                                emailFieldError = true
                                emailStatus = "*Jaza barua pepe."
                            } else if (!Patterns.EMAIL_ADDRESS.matcher(userUiState.email)
                                    .matches()
                            ) {
                                emailStatus = "*Barua pepe uliyojaza sio sahihi."
                            } else if (userUiState.pwd.isEmpty()) {
                                emailStatus = ""
                                emailFieldError = false
                                pwdFieldError = true
                                pwdStatus = "*Jaza nywila yako hapa."
                            } else if (userUiState.pwd.length < 8) {
                                pwdStatus = "*Nywila inapaswa kuwa si chini ya tarakimu nane"
                            } else {
                                /** The line below perform authentication operation */
                                /** The line below perform authentication operation */
                                loginViewModel.authenticateUser(userUiState)

                                /** Delay for 100 mil sec to wait authentication operation to finish
                                 * before sending authenticated user to the home screen.
                                 */

                                /** Delay for 100 mil sec to wait authentication operation to finish
                                 * before sending authenticated user to the home screen.
                                 */
                                coroutineScope.launch {
                                    delay(180)
                                    Log.i("request", "1: $authenticated")
                                    if (authenticated) {
                                        sessionViewModel.signIn()
                                        navController.navigate(AppRoutes.Home.name)
                                    } else {
                                        displayToast = true
                                    }
                                }

                            }
                        },
                        onSignUpClick = onSignUpButtonClicked
                    )

                    if (displayToast) {
                        DisplayToastMessage(
                            context = LocalContext.current,
                            message = "Failed to login!, double check your credentials and try again."
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun LoginHeader() {
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Member Login",
        style = MaterialTheme.typography.headlineSmall,
        color = colorResource(
            id = R.color.greenish_teal,
        )
    )
}

/** Display the toast message at the bottom if their is an error during login */
@Composable
fun DisplayToastMessage(context: Context, message: String) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        coroutineScope.launch {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

/** Login fields to chapter user login credentials */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginFields(
    email: String,
    emailStatus: String,
    emailFieldError: Boolean,
    onEmailChange: (String) -> Unit,
    pwd: String,
    pwdStatus: String,
    pwdFieldError: Boolean,
    onPwdChange: (String) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    Column() {
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

        Spacer(modifier = Modifier.height(10.dp))

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

        TextButton(onClick = {}, modifier = Modifier.align(Alignment.End)) {
            Text(
                text = "Forgot Password",
                color = colorResource(id = R.color.greenish_teal)
            )

        }
    }

    Spacer(modifier = Modifier.height(16.dp))

}


/** This is Login footer */
@Composable
fun LoginFooter(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {

    Column(
        Modifier
            .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = onSignInClick,

            modifier = Modifier
                .fillMaxWidth()

        )
        {
            Text(text = "Sign In")
        }

        TextButton(
            onClick = onSignUpClick
        ) {
            Text(
                text = "Don't have an account, Click here",
                color = colorResource(id = R.color.greenish_teal)
            )
        }
    }
}

