package com.mil.rudebot.nav

sealed class Screen(
    val route: String
) {
    object SplashPage : Screen("splash_screen")
    object ChatPage : Screen("chat_screen")
}
