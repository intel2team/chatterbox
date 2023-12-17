package com.example.chatterbox.ui.screen.bottomNavScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun ShopScreen(navController: NavHostController, innerPadding: PaddingValues) {

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(text = "상점")
        }

    }

