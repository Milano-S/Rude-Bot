package com.mil.rudebot.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mil.rudebot.R
import com.mil.rudebot.nav.Screen
import com.mil.rudebot.ui.theme.rudeBotGrey
import kotlinx.coroutines.launch


private const val TAG = "SplashScreen"

@Composable
fun SplashScreen(
    navController: NavController,
) {

    val scope = rememberCoroutineScope()
    val currentContext = LocalContext.current
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
        scope.launch {
            navController.navigate(Screen.ChatPage.route)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(rudeBotGrey)
    ) {
        Image(
            painter = painterResource(id = R.drawable.angrybot),
            contentDescription = "Logo",
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
                .align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSplash() {
    SplashScreen(navController = rememberNavController())
}