package com.example.vicoba.ui.screens.app
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vicoba.R
import com.example.vicoba.ui.navigation.AppRoutes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        val scale = remember {
            androidx.compose.animation.core.Animatable(0.0f)
        }
//        val context = LocalContext.current
//        val packageManager = context.packageManager

        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 0.7f,
                animationSpec = tween(800, easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
            )
            delay(3000)
            navController.navigate(AppRoutes.Login.name) {
                popUpTo(AppRoutes.Splash.name) {
                    inclusive = true
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge,
                color = colorResource(id = R.color.greenish_teal,
                )
            )
        }

        Text(
            text = "Version - 1.0.0.0",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}
