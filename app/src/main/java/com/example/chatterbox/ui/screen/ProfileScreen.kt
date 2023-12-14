package com.example.chatterbox.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.chatterbox.utils.UserData
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileScreen(
    userdata: UserData?,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userdata?.profilePictureUrl != null) {
            GlideImage(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                imageModel = { userdata.profilePictureUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                loading = {},
                failure = {
                    Text(text = "image request failed.")
                }

            )
        }
        if (userdata?.userName != null) {
            Text(text = userdata.userName,
                textAlign = TextAlign.Center)
        }
        Button(onClick = { onSignOut() }) {
            Text(text = "로그아웃")
        }
    }
}