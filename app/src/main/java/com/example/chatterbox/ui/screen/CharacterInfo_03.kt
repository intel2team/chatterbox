package com.example.chatterbox.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.chatterbox.R


@Composable
fun IntroScreen_03(navController: NavHostController) {
    Image(
        painter = painterResource(id = R.drawable.background_03),
        contentDescription = "Full Screen Image",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxSize() // 이미지를 화면 크기에 맞게 확장
    )
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle click here */ }) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft, // 시스템 제공 아이콘
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(45.dp, 45.dp)
                )
            }
            Spacer(modifier = Modifier.width(50.dp))
            Image(
                painter = painterResource(id = R.drawable.appname),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(195.dp, 20.dp)

            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Character \nIntroduce",
            style = TextStyle(
                fontSize = 23.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight(595),
                color = Color.Black,
                letterSpacing = 2.2.sp,
                shadow = Shadow(Color.DarkGray, offset = Offset(1.0f, 3.0f), blurRadius = 2f)
            ),
            modifier = Modifier
                .padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(100.dp))
        Image(
            painter = painterResource(id = R.drawable.smallbackground),
            contentDescription = "image description",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .padding(1.dp)
                .size(390.dp, 450.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { }) {
            Image(
                painter = painterResource(id = R.drawable.chatbutton),
                contentDescription = "ChattingButton",
                modifier = Modifier
                    .width(370.dp)
                    .height(130.dp)
                    .align(Alignment.Center)
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
//        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.introprincess),
            contentDescription = "image description",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(420.dp)
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "동물 전문가",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.roboto_thin)),
                fontWeight = FontWeight(100),
                color = Color.Black,
                letterSpacing = 2.sp,
            ),
            modifier = Modifier
                .padding(start = 33.dp)
        )
        Text(
            text = "백설공주",
            style = TextStyle(
                fontSize = 35.sp,
                fontFamily = FontFamily(Font(R.font.roboto_black)),
                fontWeight = FontWeight(1000),
                color = Color.Black,
                letterSpacing = 3.sp,
            ),
            modifier = Modifier
                .padding(start = 33.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Row(modifier = Modifier.padding(start = 37.dp)) {
            Text(
                text = "특성",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_thin)),
                    fontWeight = FontWeight(100),
                    color = Color.Black,
                    letterSpacing = 6.sp,
                ),
                modifier = Modifier.padding(start = 50.dp)
            )
            Spacer(modifier = Modifier.width(123.dp))
            Text(
                text = "MBTI",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color.Black,
                    letterSpacing = 6.sp,
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.padding(start = 30.dp)) {
            Text(
                text = "야무진 성인군자형",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontWeight = FontWeight(900),
                    color = Color.Black,
                    letterSpacing = 3.sp,
                )
            )
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                text = "ISFP",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight(900),
                    color = Color.Black,
                    letterSpacing = 6.sp,
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun IntroScreenPreview_03() {
    IntroScreen_03(navController = rememberNavController())
}