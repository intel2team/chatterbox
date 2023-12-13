package com.example.chatterbox.ui.navigation

sealed class Screen(val route: String) {
    object Onboard : Screen("onboard")
    object SignIn : Screen("sign_in")
    object Main : Screen("main")
    object Profile : Screen("profile")
    object Chat : Screen("chat")
    object Diary : Screen("diary")
}