package com.example.chatterbox.ui.screen.bottomNavScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatterbox.R
import com.example.chatterbox.ui.navigation.Screen

data class StoreItem(
    val title: String,
    val titleImage: List<Int?> = emptyList(),
    val forReleaseImage: List<Int?> = emptyList(),
    var clicked: MutableState<Boolean>
)

@Composable
fun ShopScreen(navController: NavHostController, innerPadding: PaddingValues) {
    val clicked1 = remember { mutableStateOf(true) }
    val clicked2 = remember { mutableStateOf(false) }
    val clicked3 = remember { mutableStateOf(false) }
    val storeItems = listOf(
        StoreItem(
            "감정 캐릭터",
            titleImage = listOf(
                R.drawable.insideout_joy,
                R.drawable.insideout_anger,
                R.drawable.insideout_sadness
            ),
            forReleaseImage = listOf(R.drawable.insideout_fear, R.drawable.insideout_disgust),
            clicked = clicked1
        ),
        StoreItem(
            "성능 캐릭터",
            titleImage = emptyList(),
            forReleaseImage = listOf(R.drawable.for_release1, R.drawable.for_release2),
            clicked = clicked2
        ),
        StoreItem(
            "정보 캐릭터",
            titleImage = emptyList(),
            forReleaseImage = listOf(R.drawable.for_release3),
            clicked = clicked3
        ),
    )

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        Text(
            "상점",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            storeItems.forEach { storeItem ->
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .clickable {
                            storeItems.forEach { it.clicked.value = (it == storeItem) }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = storeItem.title,
                        fontSize = if (storeItem.clicked.value) 18.sp else 14.sp,
                        fontWeight = if (storeItem.clicked.value) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
        Divider(
            thickness = 1.dp,
            color = Color.LightGray,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        storeItems.filter { it.clicked.value }.forEach {
            Text(text = it.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            LazyRow(
                modifier = Modifier
                    .height(230.dp)
                    .padding(vertical = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(it.titleImage) { item ->
                    Card(
                        modifier = Modifier
                            .size(130.dp, 230.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .clickable {
                                if (item != null) {
                                    Log.d("~~~~~~item 로그1111111~~~~~~", item.toString())
                                    navController.navigate(Screen.Detail.route + "/$item")
                                }
                            }
                    ) {
                        if (item != null) {
                            Image(
                                painter = painterResource(id = item), contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text(text = "no-data")
                        }
                    }
                }
            }
            Text(text = "곧 등장할 ${it.title}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            LazyRow(
                modifier = Modifier
                    .height(230.dp)
                    .padding(vertical = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                items(it.forReleaseImage) { item ->
                    if (item != null) {
                        if (it.title == "감정 캐릭터") {
                            Card(
                                modifier = Modifier
                                    .size(130.dp, 230.dp)
                                    .background(Color.White, RoundedCornerShape(16.dp))
                            ) {
                                Image(
                                    painter = painterResource(id = item), contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(id = item), contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .size(130.dp, 230.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                            )
                        }
                    }
                }

            }
        }
    }
}
// 이용할만함 까맣게 표시됨
//            Icon(
//                painter = painterResource(id = character.profileImage!!),
//                contentDescription = character.characterName,
//                modifier = Modifier
//                    .size(65.dp)
//                    .background(Color.Transparent, RoundedCornerShape(8.dp)),
//            )


@Composable
fun DetailScreen(navController: NavHostController, itemImage: String) {
    // 로그 4번씩 찍힘. card의 clicked 속성 업데이트로 recomposition에서 여러번의 navigation이 함께 시도된듯 ?
    Log.d("~~~~~~item 로그22222222~~~~~~", itemImage)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "back arrow")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("인사이드 아웃", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Box(
            modifier = Modifier
                .height(440.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(start = 30.dp, end = 30.dp, top = 10.dp, bottom = 20.dp)
        ) {
            Image(
                painter = painterResource(id = itemImage.toInt()), contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                Row(
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "친구추가")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xBE505050),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "체험하기")
                    }
                }
            }
        }
        Text("캐릭터가 등장한 영화", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp))
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(16.dp))
                .height(440.dp)
                .padding(vertical = 20.dp, horizontal = 30.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.insideout_poster),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}
