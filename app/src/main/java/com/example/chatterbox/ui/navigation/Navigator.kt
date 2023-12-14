package com.example.chatterbox.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatterbox.ui.screen.ChatScreen
import com.example.chatterbox.ui.screen.IntroScreen
import com.example.chatterbox.ui.screen.MainScreen
import com.example.chatterbox.ui.screen.OnboardScreen_01
import com.example.chatterbox.ui.screen.OnboardScreen_02
import com.example.chatterbox.ui.screen.OnboardScreen_03
import com.example.chatterbox.ui.screen.OnboardScreen_04

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Navigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Onboard.route) { OnboardScreen_01(navController) }
        composable(Screen.Main.route) { OnboardScreen_02(navController) }
        composable(Screen.Main.route) { OnboardScreen_03(navController) }
        composable(Screen.Main.route) { OnboardScreen_04(navController) }
//        composable(Screen.Login.route) { LoginScreen(navController) } // 작업 예정
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.Chat.route) { ChatScreen(navController) }
        composable(Screen.Intro.route) { IntroScreen(navController) }
    }
}

