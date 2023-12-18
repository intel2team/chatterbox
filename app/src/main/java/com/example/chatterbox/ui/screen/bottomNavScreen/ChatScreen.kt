package com.example.chatterbox.ui.screen.bottomNavScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatterbox.R
import com.example.chatterbox.data.local.AppDatabase
import com.example.chatterbox.data.local.entity.Assistant
import com.example.chatterbox.data.local.entity.Message
import com.example.chatterbox.ui.navigation.Screen
import com.example.chatterbox.utils.CharacterManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavHostController, innerPadding: PaddingValues) {
    val context = LocalContext.current
    val db = remember {
        AppDatabase.getDatabase(context)
    }
    val scope = rememberCoroutineScope()
    val mAuth = FirebaseAuth.getInstance()
    val currentUid = mAuth.currentUser?.uid ?: ""
    val assistants =
        db.assistantDao().getAssistantsByUid(currentUid).collectAsState(initial = emptyList()).value
    val characters = CharacterManager.getAllCharacters()
    val matchedCharacters =
        characters.filter {
            it.assistantId in assistants.map { assistant ->
                assistant.removePrefix(
                    currentUid
                )
            }
        }  // filter는 모든 객체 반환, find는 첫번째 객체 반환
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .background(Color.White)
    ) {
        Text(
            "채팅",
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .height(240.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 15.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.ratatouille),
                contentDescription = "poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(8.dp))

            )
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .height(108.dp)
                    .background(
                        Color(0xCBDADADA),
                        RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                    ),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    modifier = Modifier.padding(start = 8.dp, bottom = 12.dp),
                ) {
                    Text(text = "라따뚜이", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "2024년 1월 출시", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "륑기니 / 안톤이고 / 레미 / 구스토 출시",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
        LazyColumn() {
            item {
                Divider(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }
            items(matchedCharacters) { matchedCharacter ->
                val lastMessage =
                    db.messageDao().getLastMessageBySender(matchedCharacter.characterName!!)
                        .collectAsState(
                            initial = Message(threadId = "", senderName = "")
                        ).value
                val dismissState = rememberDismissState(
                    positionalThreshold = { it * 0.6f }, // material3는 threshold가 SwipeToDismiss 인자 값이 아님.
                    confirmValueChange = {
                        if(it == DismissValue.DismissedToStart) {
                            scope.launch {
                                withContext(Dispatchers.IO) {
                                    db.assistantDao().deleteAssistant(Assistant(currentUid + matchedCharacter.assistantId, currentUid))
                                }
                            }
                            false // 비동기 작업이 완료될 때까지 true를 반환하지 않음
                        } else {
                            true
                        }
                    }
                )
                val color by animateColorAsState(
                    when (dismissState.targetValue) {
                        DismissValue.Default -> Color.LightGray
                        else -> Color.Red
                    }, label = ""
                )
                val scale by animateFloatAsState(
                    if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f,
                    label = ""
                )
                SwipeToDismiss(state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Localized description",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    },
                    dismissContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable {
                                    val newAssistantId =
                                        currentUid + matchedCharacter.assistantId
                                    navController.navigate(Screen.Chatting.route + "/${newAssistantId}")
                                }
                                .padding(horizontal = 20.dp, vertical = 5.dp),
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth(0.8f)
                                    ) {
                                        Image(
                                            painter = painterResource(id = matchedCharacter.profileImage!!),
                                            contentDescription = matchedCharacter.characterName,
                                            modifier = Modifier
                                                .size(62.dp)
                                                .background(Color.White, RoundedCornerShape(20.dp))
                                                .border(
                                                    1.dp,
                                                    Color.Black,
                                                    RoundedCornerShape(20.dp)
                                                ),
                                            contentScale = ContentScale.Fit
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = matchedCharacter.characterName ?: "",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.ExtraBold
                                            )
                                            Spacer(modifier = Modifier.height(5.dp))
                                            Text(
                                                text = lastMessage.content ?: "",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Light,
                                                overflow = TextOverflow.Ellipsis,
                                                maxLines = 2
                                            )
                                        }
                                    }
                                    lastMessage.createAt?.let {
                                        Text(
                                            text = it,
                                            fontSize = 10.sp, fontWeight = FontWeight.Light
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
