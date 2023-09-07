
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.vicoba.R


@Composable
fun OkAlertDialog(
    icon: ImageVector,
    title:String,
    body:String,
    showDialog: Boolean,
    onDismiss: (Boolean) -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss(false)  },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Success Icon",
                        tint = colorResource(id = R.color.greenish_teal),
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = colorResource(id = R.color.greenish_teal),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            text = { Text(text=body) },
            buttons = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { onDismiss(false) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.button_color)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = MaterialTheme.shapes.medium,
                            )

                    ) {
                        Text(text = "OK", color = colorResource(id = R.color.black))
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                }
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        )
    }
}

@Preview
@Composable
fun SuccessAlertDialogPreview() {
    OkAlertDialog(
        icon = Icons.Default.CheckCircle,
        title = "Successfully",
        body = "Your request has been sent successfully.",
        showDialog = true,
        onDismiss = {})
}
