package com.mil.rudebot.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mil.rudebot.presentation.screens.ChatPage
import com.mil.rudebot.presentation.screens.SplashScreen
import com.mil.rudebot.presentation.viewmodels.RudeBotVM

@Composable
fun NavGraph(navController: NavHostController, rudeBotViewModel: RudeBotVM) {
    NavHost(navController = navController, startDestination = Screen.SplashPage.route) {

        composable(Screen.SplashPage.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.ChatPage.route){
            ChatPage(
                rudeBotViewModel
            )
        }
    }
}
