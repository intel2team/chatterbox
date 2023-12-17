package com.example.chatterbox.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterbox.R
import com.example.chatterbox.utils.Character

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    selectedCharacter: Character,
    onCloseClick: () -> Unit,
    onChatButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Image(
            painter = painterResource(id = selectedCharacter.backImage!!),
            contentDescription = "Full Screen Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            //                        verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCloseClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(42.dp))
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
                text = "캐릭터 소개",
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(100.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(16.dp)
                    ),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Spacer(modifier = Modifier.height(150.dp))
                when (selectedCharacter.characterName) {
                    "아이언맨" ->
                        cardContent(
                            title = "기계 전문가",
                            name = "아이언맨",
                            mbti1 = "천재발명가형",
                            mbti2 = "EMTP"
                        )

                    "백설공주" ->
                        cardContent(
                            title = "동물 전문가",
                            name = "백설공주",
                            mbti1 = "야무진 성인군자형",
                            mbti2 = "ISFP"
                        )

                    "올라프" ->
                        cardContent(
                            title = "겨울 요정",
                            name = "올라프",
                            mbti1 = "순수 스파크형",
                            mbti2 = "ENFP"
                        )

                    "지니" ->
                        cardContent(
                            title = "소원을 빌어주는",
                            name = "지니",
                            mbti1 = "만능 사교형",
                            mbti2 = "ESFP"
                        )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 220.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = selectedCharacter.profileImage!!),
                contentDescription = "image description",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(360.dp)
                    .height(260.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp, start = 24.dp, end = 24.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                onClick = onChatButtonClick
            ) {
                Text("위 캐릭터와 채팅하기")
            }
        }
    }
}

@Composable
fun cardContent(title: String, name: String, mbti1: String, mbti2: String) {
    Column {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                letterSpacing = 2.sp,
            ),
            modifier = Modifier
                .padding(start = 32.dp)
        )
        Text(
            text = name,
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                letterSpacing = 4.sp,
            ),
            modifier = Modifier
                .padding(start = 32.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "특성",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Start
                )
            )
            Text(
                text = "MBTI",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    letterSpacing = 2.sp,
                )
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = mbti1,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    letterSpacing = 2.sp,
                )
            )
            Text(
                text = mbti2,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    letterSpacing = 2.sp,
                )
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}