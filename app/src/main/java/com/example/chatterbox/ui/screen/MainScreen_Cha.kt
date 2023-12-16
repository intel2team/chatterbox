package com.example.chatterbox.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterbox.R



@Composable
fun MainScreen_Cha(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Handle click here */ }) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(45.dp, 45.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "인사이드 아웃 ",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_black)),
                        fontWeight = FontWeight(900),
                        color = Color.Black,
                        letterSpacing = 2.sp,
                    )
                )
            }
            Text(
                text = "행복이",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_black)),
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                    letterSpacing = 1.65.sp,
                ),
                modifier = Modifier.padding(start = 60.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Box {
                // 배경 이미지
                Image(
                    painter = painterResource(id = R.drawable.joy),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.BottomCenter)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        listOf("행복", "자신감", "긍정", "신뢰", "도전정신").forEach {
                            PosterText(text = it)
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(modifier = Modifier.clickable { }) {
                            Image(
                                painter = painterResource(id = R.drawable.posterchat),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(150.dp, 40.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))
                        Box(modifier = Modifier.clickable { }) {
                            Image(
                                painter = painterResource(id = R.drawable.postertry),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(150.dp, 40.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "인사이드아웃",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_black)),
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    letterSpacing = 3.3.sp,
                    shadow = Shadow(Color.DarkGray, offset = Offset(1.0f, 3.0f), blurRadius = 2f)
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "행복은 담당하는 매일 매일이 행복한 \n행복이",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_black)),
                    fontWeight = FontWeight(900),
                    color = Color(0xFF787878),
                    letterSpacing = 1.sp,
                    shadow = Shadow(Color.Gray, offset = Offset(1.0f, 2.0f), blurRadius = 1f)
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "유사한 캐릭터",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_black)),
                    fontWeight = FontWeight(900),
                    color = Color.Black,
                    letterSpacing = 2.2.sp,
                ),
                modifier = Modifier.padding(start = 10.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.anger),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(140.dp, 230.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.sadness),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(140.dp, 230.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.fear),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(140.dp, 230.dp)
                )
            }

            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "캐릭터가 등장한 영화",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_black)),
                    fontWeight = FontWeight(900),
                    color = Color.Black,
                    letterSpacing = 2.sp,
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.clickable { })
            {
                Image(
                    painter = painterResource(id = R.drawable.movie_poster),
                    contentDescription = "image description",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp)
                .clickable { }
        ) {
            Image(
                painter = painterResource(id = R.drawable.smallgenie),
                contentDescription = "image description",
                modifier = Modifier.size(55.dp, 56.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun PosterText(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            fontWeight = FontWeight(700),
            color = Color(0xFFFFFFFF),
            textAlign = TextAlign.Center,
            letterSpacing = 2.sp,
        )
    )
}


@Preview(showBackground = true)
@Composable
fun MainChaScreenPreview() {
    MainScreen_Cha(navController = rememberNavController())
}