package com.example.chatterbox.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.chatterbox.ui.navigation.Screen

@Composable
fun MainScreen(navController: NavHostController) {
    Column {

        Row {
            Button(onClick = { navController.navigate(Screen.Chat.route) }) {
                Text(text = "올라프")
            }
            Button(onClick = { navController.navigate(Screen.Chat.route) }) {
                Text(text = "다른 캐릭")
            }
            Button(onClick = { navController.navigate(Screen.Chat.route) }) {
                Text(text = "다른 캐릭")
            }
            Button(onClick = { navController.navigate(Screen.Chat.route) }) {
                Text(text = "다른 캐릭")
            }
        }
        Button(onClick = {
            navController.navigate(Screen.Profile.route) {
                popUpTo(Screen.Main.route)
            }
        }) {
            Text(text = "프로필")
        }
    }
}

