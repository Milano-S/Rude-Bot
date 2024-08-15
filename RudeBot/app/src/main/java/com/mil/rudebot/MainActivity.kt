package com.mil.rudebot

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.mil.rudebot.nav.NavGraph
import com.mil.rudebot.presentation.viewmodels.RudeBotVM
import com.mil.rudebot.ui.theme.RudeBotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        val rudeBotViewModel = ViewModelProvider(this)[RudeBotVM::class.java]

        setContent {
            RudeBotTheme {

                val navController = rememberNavController()

                NavGraph(
                    navController = navController,
                    rudeBotViewModel = rudeBotViewModel
                )
            }
        }
    }

    private fun getGeminiKey(): String {
        val ai: ApplicationInfo = this.packageManager.getApplicationInfo(
            this.packageName,
            PackageManager.GET_META_DATA
        )

        val value = ai.metaData["keyValue"]
        return value.toString()
    }
}
