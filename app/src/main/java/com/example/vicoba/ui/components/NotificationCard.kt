package com.example.vicoba.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.vicoba.R
import com.example.vicoba.data.models.keys.NotificationID
import com.example.vicoba.ui.viewmodels.NotificationViewModel

@Composable
fun NotificationCard(
    leftIcon: ImageVector,
    rightIcon: Painter,
    type: String,
    subRightIcon: Painter,
    title: String,
    body: String,
    time: String,
    action: () -> Unit,
    notificationID: NotificationID,
    notificationViewModel: NotificationViewModel,
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(10.dp)
            .border(width = 1.dp, color = Color.LightGray),


        ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),

            ) {
            Row() {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = "Information",
                    tint = colorResource(
                        id = R.color.greenish_teal
                    )
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = title,
                    color = colorResource(id = R.color.black)

                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = time,
                    color = colorResource(id = R.color.black)

                )
            }

            Row() {

                Text(
                    text = if (expanded) body else "${body.take(40)}...",
                    maxLines = if (expanded) Int.MAX_VALUE else 1,
                    modifier = Modifier.width(350.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    if (type == "more"){
                        expanded = !expanded
                        notificationViewModel.deleteViewedNotification(notificationID)
                    } else action()
                }) {
                    Image(
                        painter = if (expanded) subRightIcon else rightIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        }
    }
}