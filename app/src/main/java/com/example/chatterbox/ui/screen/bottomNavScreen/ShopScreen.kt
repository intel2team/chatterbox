package com.example.chatterbox.ui.screen.bottomNavScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ShopScreen(navController: NavHostController, innerPadding: PaddingValues) {

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            // 이용할만함 까맣게 표시됨
//            Icon(
//                painter = painterResource(id = character.profileImage!!),
//                contentDescription = character.characterName,
//                modifier = Modifier
//                    .size(65.dp)
//                    .background(Color.Transparent, RoundedCornerShape(8.dp)),
//            )
            Text(text = "상점")
        }

    }

