package com.example.chatterbox.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatterbox.ui.screen.ChatScreen
import com.example.chatterbox.ui.screen.DiaryScreen
import com.example.chatterbox.ui.screen.MainScreen
import com.example.chatterbox.ui.screen.OnboardScreen

@Composable
fun Navigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) { OnboardScreen(navController) }
//        composable(Screen.Login.route) { LoginScreen(navController) } // 작업 예정
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.Chat.route) { ChatScreen(navController) }
        composable(Screen.Diary.route) { DiaryScreen(navController) }
    }
}

