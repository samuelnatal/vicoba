package com.example.vicoba.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.vicoba.R

@Composable
fun ArrowForwardCard(
    icon:ImageVector,
    title:String,
    desc:String
){
    Card(
        modifier = Modifier
            .padding(10.dp)
            .border(width = 1.dp, color = Color.LightGray),


        ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {
                Icon(
                    imageVector = icon,
                    contentDescription = "Information",
                    tint = colorResource(
                        id = R.color.greenish_teal
                    )
                )
                Text(
                    text = title,
                    color = colorResource(id = R.color.black)

                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = desc
            )
        }
    }
}