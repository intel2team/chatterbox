package com.example.chatterbox.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatterbox.R

//@Composable
//fun LoginScreen(navController: NavHostController) {
//    Box(
//        modifier = Modifier
//            .shadow(
//                elevation = 4.dp,
//                spotColor = Color(0x40000000),
//                ambientColor = Color(0x40000000)
//            )
//            .width(360.dp)
//            .height(800.dp)
//            .background(color = Color(0xFF000000))
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.appname),
//            contentDescription = "image description",
//            contentScale = ContentScale.Fit,
//            modifier = Modifier
//                .padding(start = 136.dp, top = 42.dp)
//                .width(84.53969.dp)
//                .height(14.48.dp)
//        )
//        Column(
//            modifier = Modifier
//                .padding(top = 350.dp)
//                .align(Alignment.Center)
//        ) {
//            IconButton(
//                onClick = { },
//                modifier = Modifier
//                    .width(250.dp)
//                    .height(54.dp)
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.facebook),
//                    contentDescription = "ImageButton",
//                    modifier = Modifier
//                        .width(250.dp)
//                        .height(54.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(9.dp))
//            IconButton(
//                onClick = { },
//                modifier = Modifier
//                    .width(300.dp)
//                    .height(54.dp)
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.google),
//                    contentDescription = "ImageButton",
//                    modifier = Modifier
//                        .width(300.dp)
//                        .height(54.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(9.dp))
//            IconButton(
//                onClick = { },
//                modifier = Modifier
//                    .width(281.dp)
//                    .height(54.dp)
//            ) {
//                Image(
////                    painter = painterResource(id = R.drawable.apple),
//                    contentDescription = "ImageButton",
//                    modifier = Modifier
//                        .width(281.dp)
//                        .height(54.dp)
//                )
//            }
//
//        }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(navController = rememberNavController())
//}