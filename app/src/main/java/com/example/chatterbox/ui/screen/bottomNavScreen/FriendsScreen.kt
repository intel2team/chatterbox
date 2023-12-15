package com.example.chatterbox.ui.screen.bottomNavScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.client.OpenAI
import com.example.chatterbox.BuildConfig
import com.example.chatterbox.R
import com.example.chatterbox.data.local.AppDatabase
import com.example.chatterbox.ui.navigation.BottomNavItem
import com.example.chatterbox.utils.UserData
import com.google.firebase.auth.FirebaseAuth
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay

data class Character(
    val characterName: String,
    val assistantId: String,
    val statusMessage: String,
    val profileImage: Int,
    var isFavorite: Boolean = false
)

object CharacterManager {
    private val IRON_MAN = Character(
        "아이언맨",
        "asst_wOV61PAkQH42T1EzyFP8Lwbd",
        statusMessage = "I'm Iron Man",
        profileImage = R.drawable.ironman_profile
    )
    private val SNOW_WHITE = Character(
        "백설공주",
        "asst_F9LOI0D2bEHxrXElKoAJdDRb",
        statusMessage = "안녕, 난 백설공주",
        profileImage = R.drawable.snow_white_profile
    )
    private val OLAF = Character(
        "올라프",
        "asst_pKRMmMds8HVj6cWNmhhjZxGh",
        statusMessage = "겨울이 좋아",
        profileImage = R.drawable.olaf_profile
    )
    private val JENIE = Character(
        "지니",
        "asst_6JEfd9K0gVT0fhF9TWLXLKd9",
        statusMessage = "소원을 말해봐",
        profileImage = R.drawable.genie_profile
    )

    fun getAllCharacters(): List<Character> {
        return listOf(IRON_MAN, SNOW_WHITE, OLAF, JENIE)
    }
}


@OptIn(BetaOpenAI::class)
@Composable
fun FriendsScreen(navController: NavHostController, userdata: UserData?) {
    val context = LocalContext.current
    val db = remember {
        AppDatabase.getDatabase(context)
    }
    val openAI = OpenAI(BuildConfig.OPENAI_API_KEY)
    val mAuth = FirebaseAuth.getInstance()
    val currentUid = mAuth.currentUser?.uid ?: ""
    val assistants =
        db.assistantDao().getAssistantsByUid(currentUid).collectAsState(initial = emptyList()).value
    val characters = CharacterManager.getAllCharacters()
    val scope = rememberCoroutineScope()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                item {
                    Text(
                        "친구",
                        modifier = Modifier.padding(20.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                item {
                    if (userdata?.profilePictureUrl != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable {  }
                                .padding(horizontal = 20.dp, vertical = 5.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row {
                                    GlideImage(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape),
                                        imageModel = { userdata.profilePictureUrl },
                                        imageOptions = ImageOptions(
                                            contentScale = ContentScale.Crop,
                                            alignment = Alignment.Center
                                        ),
                                        loading = { Text("로딩중") },
                                        failure = {
                                            Text(text = "failed.")
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    userdata.userName?.let {
                                        Text(
                                            text = it,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    AdBanner() // 광고 배너
                }
                // 즐겨찾기
                item {
                    Text(
                        "즐겨찾기",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
//            items(favoriteFriends) { friend ->
//                FriendItem(
//                    friend = friend,
//                    onToggleFavorite = { friendId ->
//                        viewModel.toggleFavorite(friendId)
//                    },
//                    isFavorite = true // 즐겨찾기된 친구에 대한 강조 표시
//                )
//            }
                // 나머지 친구들
                item {
                    Text(
                        "친구",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                items(characters) { character ->
                    CharacterRow(character) {
//                    navController.navigate(Screen.Chatting.route)
                    }
                }
            }
        }
    }
//    Column {
//        Row {
//            Button(onClick = {
//            }) {
//                Text(text = "아이언맨")
//            }
//            Button(onClick = { navController.navigate(Screen.Chatting.route) }) {
//                Text(text = "백설공주")
//            }
//            Button(onClick = {
//                val newAssistantId = currentUid + CharacterManager.getAllCharacters()[3].assistantId
//                if (newAssistantId !in assistants) {
//                    val newAssistant = Assistant(newAssistantId, currentUid)
//                    scope.launch(Dispatchers.IO) {
//                        db.assistantDao().insertAssistantAll(newAssistant)
//                        val threadId = openAI.thread().id.id
//                        println("*****thread id 찍어봐 : $threadId")
//                        val thread = Thread(threadId, newAssistant.assistantId)
//                        db.threadDao().insertThreadAll(thread)
//                    }
//                }
//                Handler(Looper.getMainLooper()).postDelayed({
//                    navController.navigate(Screen.Chatting.route + "/${newAssistantId}")
//                }, 1000)
//            }) {
//                Text(text = "올라프")
//            }
//            Button(onClick = {
////                scope.launch {
////                    val thread = openAI.thread()
////                    println("222222222$thread")
////                    val threadId = openAI.thread().id.id
////                    println("*****22222thread id 찍어봐 : $threadId")
////                }
////                navController.navigate(Screen.Chat.route)
//            }) {
//                Text(text = "지니")
//            }
//        }
//        Button(onClick = {
//            navController.navigate(Screen.Profile.route) {
//                popUpTo(BottomNavItem.Friends.route)
//            }
//        }) {
//            Text(text = "프로필")
//        }
//    }
}

@Composable
fun CharacterRow(character: Character, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 5.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Image(
                        painter = painterResource(id = character.profileImage),
                        contentDescription = character.characterName,
                        modifier = Modifier.height(40.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = character.characterName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = character.statusMessage,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
        }
    }
}

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

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Friends,
        BottomNavItem.Chat,
        BottomNavItem.Shop,
        BottomNavItem.Others
    )
    BottomNavigation(
        backgroundColor = Color(0xFFD8D8D8),
        contentColor = Color.Black
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = navController.currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

