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
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterbox.R


@Composable
fun MainScreen_Full(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(15.dp)
        ) {
            Text(
                text = "전체보기",
                style = TextStyle(
                    fontSize = 23.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_black)),
                    fontWeight = FontWeight(900),
                    color = Color.Black,
                    letterSpacing = 2.sp,
                )
            )
            Spacer(modifier = Modifier.height(25.dp))

            Box {
                // 배경 이미지
                Image(
                    painter = painterResource(id = R.drawable.mainback),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 30.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        listOf("AI 전문가", "자신감", "거만함", "책임감", "도전정신").forEach {
                            CardText(text = it)
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
                                    .size(140.dp, 40.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))
                        Box(modifier = Modifier.clickable { }) {
                            Image(
                                painter = painterResource(id = R.drawable.postertry),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(140.dp, 40.dp)
                            )
                        }
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.introironman),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(350.dp)
                        .height(300.dp)
                        .padding(top = 20.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 20.dp, end = 40.dp)
                ) {
                    Text(
                        text = "아이언맨",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_black)),
                            fontWeight = FontWeight(900),
                            color = Color(0xFFFEFBFB),
                            textAlign = TextAlign.Center,
                            letterSpacing = 6.sp,
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "오늘 TOP 5 캐릭터",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_black)),
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.num_one),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(124.dp, 160.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.num_two),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(124.dp, 168.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.num_three),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(124.dp, 155.dp)
                )
            }

            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "인기 있는 영화 캐릭터",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_black)),
                    fontWeight = FontWeight(900),
                    color = Color.Black,
                    letterSpacing = 2.sp,
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.popular_01),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp, 158.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.popular_02),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp, 158.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.popular_03),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp, 158.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.popular_04),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp, 158.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "인기 있는 브랜드 캐릭터",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_black)),
                    fontWeight = FontWeight(900),
                    color = Color.Black,
                    letterSpacing = 2.sp,
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(1.dp)
                        .width(88.dp)
                        .height(114.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.brandback_01),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        painter = painterResource(id = R.drawable.marvel_logo),
                        contentDescription = "Image Content Description",
                        modifier = Modifier
                            .fillMaxSize() // 이미지를 수직으로 가득 차게 설정합니다.
                            .align(Alignment.Center) // 수직 가운데 정렬합니다.
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(1.dp)
                        .width(88.dp)
                        .height(114.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.brandback_02),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        painter = painterResource(id = R.drawable.pixar_logo),
                        contentDescription = "Image Content Description",
                        modifier = Modifier
                            .fillMaxSize() // 이미지를 수직으로 가득 차게 설정합니다.
                            .align(Alignment.Center) // 수직 가운데 정렬합니다.
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(1.dp)
                        .width(88.dp)
                        .height(114.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.brandback_03),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        painter = painterResource(id = R.drawable.disney_logo),
                        contentDescription = "Image Content Description",
                        modifier = Modifier
                            .fillMaxSize() // 이미지를 수직으로 가득 차게 설정합니다.
                            .align(Alignment.Center) // 수직 가운데 정렬합니다.
                    )
                }
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
fun CardText(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 10.sp,
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
fun MainFullScreenPreview() {
    MainScreen_Full(navController = rememberNavController())
}