package com.example.chatterbox.ui.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.assistant.AssistantId
import com.aallam.openai.api.core.Role
import com.aallam.openai.api.core.Status
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.message.MessageContent
import com.aallam.openai.api.message.MessageRequest
import com.aallam.openai.api.run.RunRequest
import com.aallam.openai.api.thread.ThreadId
import com.aallam.openai.client.OpenAI
import com.example.chatterbox.BuildConfig
import com.example.chatterbox.data.local.AppDatabase
import com.example.chatterbox.data.local.entity.Message
import com.example.chatterbox.ui.screen.bottomNavScreen.Character
import com.example.chatterbox.ui.screen.bottomNavScreen.CharacterManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(BetaOpenAI::class)
@Composable
fun ChattingScreen(navController: NavHostController, newAssistantId: String) {
    val context = LocalContext.current
    val db = remember {
        AppDatabase.getDatabase(context)
    }
    val scope = rememberCoroutineScope()
    val apiKey = BuildConfig.OPENAI_API_KEY
    val openAI = OpenAI(
        token = apiKey,
        timeout = Timeout(socket = 60.seconds)
    )
    val threadId by
    db.threadDao().getThreadIdByAssistantId(newAssistantId).collectAsState(initial = "")
    // thread id를 동일하게 써서 getMessagesByThreadId가 db에서 flow를 관찰하지 않는 듯함.
    // boolean 값을 이용해 boolean 값이 변경되면 동작하도록 설정해보았음.
    val messageList by db.messageDao().getMessagesByThreadId(threadId)
        .collectAsState(emptyList())

    val mAuth = FirebaseAuth.getInstance()
    val currentUid = mAuth.currentUser?.uid!!
    val assistantId = newAssistantId.removePrefix(currentUid)
    var userText: String by remember { mutableStateOf("") }
    val characterNameFromAssistantId =
        CharacterManager.getAllCharacters().find { it.assistantId == assistantId }?.characterName ?: ""
//        CharacterManager.find { it.assistantId == assistantId }?.characterName ?: ""

//    var assistant: Assistant?
//    LaunchedEffect(Unit) {
//        assistant = openAI.assistant(id = AssistantId(assistantId),
//            request = AssistantRequest(
//                instructions = "수정 instruction",
//                tools = listOf(AssistantTool.RetrievalTool), // tool 추가 가능
//                model = ModelId("gpt-4"), // 모델 변경
//                fileIds = listOf(FileId("file-abc123"), FileId("file-abc123")), // 파일 추가가능
//            )
//        )
//    }
    val scrollState = rememberLazyListState()
    if (messageList.isNotEmpty()) {
        LaunchedEffect(messageList.lastIndex) {
            scrollState.scrollToItem(index = messageList.lastIndex)
        }
    }
    var isResponse: Boolean by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6F2FF))
    ) {
        Column {
            TopBar(navController, characterNameFromAssistantId)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.92f)
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.Bottom,
                state = scrollState
            ) {
                items(messageList) { message ->
                    MessageContent(message)
                }
                if (isResponse) {
                    item {
                        LoadingMessageWithAnimation()
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            TextField(value = userText, onValueChange = { userText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .background(Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTrailingIconColor = Color(0xFF22ABF3),
                    unfocusedTrailingIconColor = Color(0xFF22ABF3),
                    cursorColor = Color(0xFF22ABF3),
                    unfocusedTextColor = Color(0xFF22ABF3),
                    focusedTextColor = Color(0xFF22ABF3)
                ),
                placeholder = {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFF22ABF3),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            ) {
                                append("$characterNameFromAssistantId")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFF22ABF3),
                                    fontSize = 16.sp,
                                )
                            ) {
                                append("와 대화해보세요!")
                            }
                        })
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            scope.launch(Dispatchers.IO) {
                                openAI.message(
                                    threadId = ThreadId(threadId),
                                    request = MessageRequest(
                                        role = Role.User,
                                        content = userText
                                    )
                                )
                                // userText db 추가
                                val userMessage =
                                    Message(
                                        threadId = threadId,
                                        userRole = true,
                                        content = userText
                                    )
                                db.messageDao().insertMessageAll(userMessage)
                                isResponse = true
                                userText = ""
                                val run = openAI.createRun(
                                    threadId = ThreadId(threadId),
                                    request = RunRequest(assistantId = AssistantId(assistantId)),
                                )
                                do {
                                    delay(1000)
                                    val retrievedRun =
                                        openAI.getRun(threadId = ThreadId(threadId), runId = run.id)
                                } while (retrievedRun.status != Status.Completed)
                                val assistantMessages = openAI.messages(ThreadId(threadId))
                                val textContext =
                                    assistantMessages[0].content.first() as MessageContent.Text
                                // textContext.value db 추가
                                val chatMessage = Message(
                                    threadId = threadId,
                                    userRole = false,
                                    content = textContext.text.value
                                )
                                db.messageDao().insertMessageAll(chatMessage)
                                isResponse = false
                            }
                        },
                        enabled = userText.isNotBlank() || isResponse
                    ) {
                        Icon(imageVector = Icons.Filled.Send, contentDescription = "send")
                    }
                })
//            TextFieldComponent(assistantId, threadId, isResponse, hasReload)
        }
    }
}

@Composable
fun TopBar(navController: NavHostController, characterNameFromAssistantId: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "arrow back")
        }
        Text(
            text = characterNameFromAssistantId,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun MessageContent(message: Message) {
    Column(
        modifier = Modifier
            .padding(8.dp)
//            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = if (message.userRole) Alignment.End else Alignment.Start,
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (message.userRole) Color(0xFF2FCC59) else Color(0xFF22ABF3),
                    RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message.content,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp),
                textAlign = if (message.userRole) TextAlign.End else TextAlign.Start
            )
        }
    }
}

@Composable
fun LoadingMessageWithAnimation() {
    var progress by remember { mutableStateOf(0f) }
    val fadeInOutAlpha = rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .background(
                Color(0xFF22ABF3),
                RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "답변 생성 중...",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 8.dp)
                .alpha(fadeInOutAlpha.value)
        )
    }
}