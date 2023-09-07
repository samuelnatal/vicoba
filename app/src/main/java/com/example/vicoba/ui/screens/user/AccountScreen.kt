package com.example.vicoba.ui.screens.user

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vicoba.R
import com.example.vicoba.data.models.entities.ActiveKikobaUser
import com.example.vicoba.ui.components.popups.ActionAlertDialog
import com.example.vicoba.ui.components.ArrowForwardCard
import com.example.vicoba.ui.navigation.AppRoutes
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.viewmodels.LoginViewModel
import com.example.vicoba.ui.viewmodels.SessionViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountScreen(
    loginViewModel: LoginViewModel,
    sessionViewModel: SessionViewModel,
    navController: NavController
) {

    /** user variable holds the information of the active user */
    val user = KikobaUserState.userState.collectAsState()

    Spacer(modifier = Modifier.height(100.dp))
    Column(
        modifier = Modifier
            .height(600.dp)
            .fillMaxSize()
            .padding(20.dp)
            .background(colorResource(id = R.color.light_grayish_blue))
    ) {

        ProfilePictureCard(
            user = user.value.user
        )

        val cardInfos = setOf(
            Triple(
                "My Information",
                Icons.Rounded.Person,
                "Click to view your Information"
            ),
            Triple("Kikoba Document", Icons.Default.Info, "Vicoba app documentation"),
            Triple(
                "Terms and condition",
                Icons.Rounded.Done,
                "Click to view vicoba terms and condition"
            ),
            Triple("Update profile", Icons.Rounded.Edit, "Click to update your profile")
        )

        var logoutStatus by remember { mutableStateOf(false) }

        cardInfos.forEach { triple ->
            val (title, icon, desc) = triple
            ArrowForwardCard(icon = icon, title = title, desc = desc)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { logoutStatus = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.button_color)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = MaterialTheme.shapes.medium,
                )

        ) {
            Text(text = "Logout", color = colorResource(id = R.color.black))
        }

        ActionAlertDialog(
            title = "Logout",
            body = "Do you real want to logout?",
            showDialog = logoutStatus,
            onAccept = {
                sessionViewModel.logOut()
                loginViewModel.resetAuth()
                navController.navigate(AppRoutes.Login.name)
            },
            onDismiss = { showDialogStatus ->
                logoutStatus = showDialogStatus
            }

        )
    }
}

@Composable
fun ProfilePictureCard(
    user: ActiveKikobaUser
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Spacer(modifier = Modifier.height(10.dp))

        Image(
            painter = painterResource(id = R.drawable.cupcake),
            contentDescription = stringResource(id = R.string.account_text),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .border(
                    BorderStroke(4.dp, Color.Green),
                    CircleShape
                )
                .padding(4.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "${user.firstName}  ${user.lastName}",
            fontSize = 30.sp,
        )
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()

    val loginViewModel: LoginViewModel =
        viewModel(factory = LoginViewModel.loginFactory)

    AccountScreen(
        sessionViewModel = SessionViewModel(),
        loginViewModel = loginViewModel,
        navController = navController,
    )
}