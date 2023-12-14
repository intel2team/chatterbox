package com.example.chatterbox.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatterbox.R

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun OnboardScreen_02(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFBFBFB))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.rectangle_2),
                contentDescription = "background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(500.dp)
                    .height(600.dp)
                    .padding(top = 20.dp)
            )
            Text(
                text = "Dairy",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                    letterSpacing = 2.75.sp,
                ),
                modifier = Modifier
                    .padding(start = 33.dp)
            )
            Text(
                text = "캐릭터의 대화가 간편하고 쉽게\n일기로 기록되고 그날 어떤 기분을 가지고\n있는지 기록이 됩니다.",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp,
                ),
                modifier = Modifier
                    .padding(start = 51.dp, top = 8.dp)
            )
            Row(   //쪼만한 버튼 4개
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(13.dp, 13.dp)
                        .background(color = Color(0xFFF9F9F9), shape = RoundedCornerShape(50.dp))
                        .background(
                            color = Color(0x42384212),
                            shape = RoundedCornerShape(size = 50.dp)
                        )
//                    .clickable { onClick() } 클릭 이벤트
                )
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .size(33.dp, 13.dp)
                        .background(
                            color = Color(0xFF048ABF),
                            shape = RoundedCornerShape(50.dp)
                        )
//                    .clickable { onClick() } 클릭 이벤트
                )
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .size(13.dp, 13.dp)
                        .background(color = Color(0xFFF9F9F9), shape = RoundedCornerShape(50.dp))
                        .background(
                            color = Color(0x42384212),
                            shape = RoundedCornerShape(size = 50.dp)
                        )
//                    .clickable { onClick() } 클릭 이벤트
                )
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .size(13.dp, 13.dp)
                        .background(color = Color(0xFFF9F9F9), shape = RoundedCornerShape(50.dp))
                        .background(
                            color = Color(0x42384212),
                            shape = RoundedCornerShape(size = 50.dp)
                        )
//                    .clickable { onClick() } 클릭 이벤트
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = "ImageButton",
                    modifier = Modifier
                        .width(350.dp)
                        .height(80.dp)
                        .align(Alignment.Center)
                )
            }

        }
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 45.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.appname),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(85.dp)
                    .height(15.dp)

            )
            Spacer(modifier = Modifier.height(60.dp)) // Add vertical spacing
            Image(
                painter = painterResource(id = R.drawable.princess),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(500.dp)
                    .height(490.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun OnboardScreenPreview_02() {
    OnboardScreen_02(navController = rememberNavController())
}
