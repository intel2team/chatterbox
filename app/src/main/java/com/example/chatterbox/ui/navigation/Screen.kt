package com.example.chatterbox.ui.navigation

sealed class Screen(val route: String) {
    object Onboard : Screen("onboard")
    object Profile : Screen("profile")
    object Chatting : Screen("chatting/{assistantId}")
}