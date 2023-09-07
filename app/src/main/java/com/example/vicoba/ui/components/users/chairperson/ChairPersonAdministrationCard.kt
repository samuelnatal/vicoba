package com.example.vicoba.ui.components.users.chairperson

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vicoba.R
import com.example.vicoba.ui.components.ManagementCard
import com.example.vicoba.ui.navigation.AppRoutes

@Composable
fun ChairPersonAdministrationCard(
    navHostController: NavHostController
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(10.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Row(
        ) {
            Text(
                text = "Kazi za uenyekiti",
                style = MaterialTheme.typography.headlineSmall,
                color = colorResource(id = R.color.greenish_teal)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                expanded = !expanded
            }) {
                Icon(
                    painter = if (expanded) painterResource(id = R.drawable.expandless) else painterResource(
                        id = R.drawable.expandmore
                    ),
                    contentDescription = "Show administrative jobs",
                )
            }
        }

        if (expanded) {
            ManagementCard(
                icon = Icons.Rounded.Person,
                text = "Idhinisha maombi ya mikopo.",
                onClickManagementCard = {
                    navHostController.navigate(AppRoutes.ApproveLoans.name)
                }
            )
        }
    }
}