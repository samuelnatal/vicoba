package com.example.vicoba.ui.components.users.member

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vicoba.R
import com.example.vicoba.ui.navigation.AppRoutes

@Composable
fun MemberAdministrationCard(
    navHostController: NavHostController
) {

    Row(
        horizontalArrangement =  Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedButton(
            border = BorderStroke(
                width = 1.dp,
                color = colorResource(id = R.color.bright_yellow)
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                navHostController.navigate(AppRoutes.BuyShare.name)
            },
        ) {
            Text(text = "Nununa hisa")
        }

        OutlinedButton(
            border = BorderStroke(
                width = 1.dp,
                color = colorResource(id = R.color.dark_green)
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {
            navHostController.navigate(AppRoutes.RequestLoan.name)
        }) {
            Text(text = "Omba mkopo")
        }

        OutlinedButton(
            border = BorderStroke(
                width = 1.dp,
                color = colorResource(id = R.color.bright_yellow)
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                navHostController.navigate(AppRoutes.MyDebts.name)
            }) {
            Text(text = "Lipa madeni")
        }
    }
}