package com.example.vicoba.ui.components.bars

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vicoba.R
import com.example.vicoba.data.models.keys.UserID
import com.example.vicoba.ui.navigation.AppRoutes
import com.example.vicoba.ui.states.KikobaUserState
import com.example.vicoba.ui.viewmodels.NotificationViewModel

@Composable
fun VicobaBottomAppBar(
    notificationViewModel: NotificationViewModel,
    navController: NavController
) {
    /** user variable holds the information of the active user */
    val user = KikobaUserState.userState.collectAsState()

    /** set user key to be used by the home screen and get shared to other screen too*/
    val userID = UserID(
        userID = user.value.user.userID
    )
    BottomNavigation(
        contentColor = Color.Black,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {

        BottomNavigationItem(
            selected = true,
            onClick = {
                /* Handle item click */
                navController.navigate("Home")
            },
            unselectedContentColor = colorResource(id = R.color.button_color),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Dashboard",
                )
            },
        )
        BottomNavigationItem(
            selected = false,
            onClick = {
                /* Handle item click */
                navController.navigate("search")
            },
            icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
        )
        BottomNavigationItem(
            selected = false,
            onClick = {
                notificationViewModel.getUserNotifications(userID)
                navController.navigate("notification")
            },
            icon = {
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = "Notification"
                )
            },
        )
        BottomNavigationItem(
            selected = false,
            onClick = {
                /* Handle item click */
                navController.navigate(AppRoutes.Account.name)
            },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
        )
    }
}



