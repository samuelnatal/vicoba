package com.example.vicoba.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vicoba.R

@Composable
fun DashboardTopCard(
    kikobaName: String,
    kikobaWallet: Double,
    cornerRadius: Dp = 30.dp,
) {
    Column(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = cornerRadius, bottomEnd = cornerRadius
                )
            )
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start, // Align content horizontally to the start
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start, // Align text horizontally to the start
        ) {
            Text(
                text = kikobaName,
                fontSize = 25.sp,
                textAlign = TextAlign.Start, // Set text alignment to start (left)
                style = MaterialTheme.typography.bodyLarge,
            )
            Row() {
                Image(
                    painter = painterResource(R.drawable.money_bag),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
                Text(
                    text = "  $kikobaWallet Tsh",
                    textAlign = TextAlign.Start, // Set text alignment to start (left)
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.button_color)
                )
            }

        }
    }
}
