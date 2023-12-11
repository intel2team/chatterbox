package com.example.chatterbox.ui.navigation

sealed class Screen(val route: String) {
    object Onboard : Screen("onboard")
    //    object Login : Screen("login")
    object Main : Screen("main")
    object Chat : Screen("chat")
    object Diary : Screen("diary")
}