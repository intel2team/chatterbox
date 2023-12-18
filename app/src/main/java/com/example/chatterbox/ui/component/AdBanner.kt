package com.example.chatterbox.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chatterbox.R
import kotlinx.coroutines.delay

@Composable
fun AdBanner() {
    // 이미지 URL 리스트
    val bannerImage = listOf(
        R.drawable.aladin_banner,
        R.drawable.car_banner,
        R.drawable.tazzan_banner
    )
    // 현재 이미지 인덱스 상태
    val currentIndex = remember { mutableStateOf(0) }

    // 5초마다 이미지 변경
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000) // 5초 대기
            currentIndex.value = (currentIndex.value + 1) % bannerImage.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(10.dp)
            .background(Color.White, RoundedCornerShape(16.dp))

    ) {
        Image(
            painter = painterResource(id = bannerImage[currentIndex.value]),
            contentDescription = "광고 배너",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
    }
}