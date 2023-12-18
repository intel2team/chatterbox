package com.example.chatterbox.ui.screen.bottomNavScreen

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.chatterbox.data.local.AppDatabase
import com.example.chatterbox.data.local.entity.Assistant
import com.example.chatterbox.data.local.entity.Message
import com.example.chatterbox.data.local.entity.Thread
import com.example.chatterbox.ui.component.AdBanner
import com.example.chatterbox.ui.component.BottomSheetContent
import com.example.chatterbox.ui.navigation.Screen
import com.example.chatterbox.utils.Character
import com.example.chatterbox.utils.CharacterManager
import com.example.chatterbox.utils.UserData
import com.google.firebase.auth.FirebaseAuth
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(BetaOpenAI::class, ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    navController: NavHostController,
    userdata: UserData?,
    innerPadding: PaddingValues
) {
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
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCharacter by remember { mutableStateOf(Character()) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .background(Color.White)
    ) {
        LazyColumn {
            item {
                Text(
                    "친구",
                    modifier = Modifier.padding(20.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
            item {
                if (userdata?.profilePictureUrl != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .clickable { navController.navigate(Screen.Profile.route) }
                            .padding(horizontal = 20.dp, vertical = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row {
                                GlideImage(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .background(Color.White, RoundedCornerShape(16.dp))
                                        .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                                        .clip(shape = RoundedCornerShape(16.dp)),
                                    imageModel = { userdata.profilePictureUrl },
                                    imageOptions = ImageOptions(
                                        contentScale = ContentScale.Fit,
                                        alignment = Alignment.Center
                                    ),
                                    loading = { CircularProgressIndicator() },
                                    failure = {
                                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
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
            item {
                Text(
                    "즐겨찾기",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                Divider(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp), thickness = 1.dp, color = Color.LightGray)
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
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                Divider(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp), thickness = 1.dp, color = Color.LightGray)
            }
            items(characters) { character ->
                CharacterRow(character) {
                    selectedCharacter = character
                    showBottomSheet = true
                }
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxSize(),
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                BottomSheetContent(selectedCharacter, onCloseClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                },
                    onChatButtonClick = {
                        val newAssistantId =
                            currentUid + selectedCharacter.assistantId
                        if (newAssistantId !in assistants) {
                            val newAssistant = Assistant(newAssistantId, currentUid)
                            scope.launch(Dispatchers.IO) {
                                db.assistantDao().insertAssistantAll(newAssistant)
                                val threadId = openAI.thread().id.id
//                            println("*****thread id 찍어봐 : $threadId")
                                val thread = Thread(threadId, newAssistant.assistantId)
                                db.threadDao().insertThreadAll(thread)
                                val message = Message(
                                    threadId = threadId,
                                    senderName = selectedCharacter.characterName!!
                                )
                                db.messageDao().insertMessageAll(message)
                            }
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            navController.navigate(Screen.Chatting.route + "/${newAssistantId}")
                        }, 1000)
                    })
            }
        }
    }
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
                        painter = painterResource(id = character.profileImage!!),
                        contentDescription = character.characterName,
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = character.characterName!!,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = character.statusMessage!!,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
        }
    }
}

//@Composable
//fun BottomNavigationBar(navController: NavHostController) {
//    val items = listOf(
//        BottomNavItem.Friends,
//        BottomNavItem.Chat,
//        BottomNavItem.Shop,
//        BottomNavItem.Others
//    )
//    BottomNavigation(
//        backgroundColor = Color(0xFFE0E0E0),
//        contentColor = Color.Black
//    ) {
//        items.forEach { item ->
//            BottomNavigationItem(
//                icon = { Icon(item.icon, contentDescription = item.title) },
//                label = { Text(item.title, fontSize = 12.sp) },
//                selected = navController.currentDestination?.route == item.route,
//                onClick = {
//                    navController.navigate(item.route) {
//                        popUpTo(navController.graph.startDestinationId)
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
//    }
//}
//
