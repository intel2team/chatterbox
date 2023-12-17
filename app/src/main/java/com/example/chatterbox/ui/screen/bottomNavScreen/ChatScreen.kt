package com.example.chatterbox.ui.screen.bottomNavScreen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.aallam.openai.client.OpenAI
import com.example.chatterbox.BuildConfig
import com.example.chatterbox.R
import com.example.chatterbox.data.local.AppDatabase
import com.example.chatterbox.data.local.entity.Message
import com.example.chatterbox.ui.navigation.Screen
import com.example.chatterbox.utils.CharacterManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

@Composable
fun ChatScreen(navController: NavHostController, innerPadding: PaddingValues) {
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
    val matchedCharacters =
        characters.filter {
            it.assistantId in assistants.map { assistant ->
                assistant.removePrefix(
                    currentUid
                )
            }
        }  // filter는 모든 객체 반환, find는 첫번째 객체 반환
    val scope = rememberCoroutineScope()
    var lastMessage: Message by remember { mutableStateOf(Message(threadId = "", senderName = "")) }
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "채팅",
            fontWeight = FontWeight.ExtraBold
        )
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.ratatouille),
                contentDescription = "poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "라따뚜이 2024년 1월 출시")
            Text(text = "륑기니 / 안톤이고 / 레미 / 구스토 출시")
        }
        LazyColumn() {
            items(matchedCharacters) { matchedCharacter ->
                LaunchedEffect(matchedCharacter) {
                    withContext(Dispatchers.IO) {
                        lastMessage =
                            db.messageDao().getLastMessage(matchedCharacter.characterName!!)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .clickable {
                            val newAssistantId =
                                currentUid + matchedCharacter.assistantId
                            navController.navigate(Screen.Chatting.route + "/${newAssistantId}")
                        }
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
                                    painter = painterResource(id = matchedCharacter.profileImage!!),
                                    contentDescription = matchedCharacter.characterName,
                                    modifier = Modifier.height(40.dp),
                                    contentScale = ContentScale.Crop
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
                                        fontWeight = FontWeight.Light
                                    )
                                }
                            }
                            Text(text = lastMessage.timestamp.toString())
                        }
                    }
                }
            }
        }
    }
}
