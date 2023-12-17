package com.example.chatterbox.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chatterbox.R
import kotlinx.coroutines.delay

@Composable
fun AdBanner() {
    // 이미지 URL 리스트
    val bannerImage = listOf(
        R.drawable.marvel,
        R.drawable.marvel,
        R.drawable.marvel
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
    Image(
        painter = painterResource(id = bannerImage[currentIndex.value]),
        contentDescription = "광고 배너",
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp) // 광고 배너의 높이
    )
}