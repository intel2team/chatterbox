package com.example.chatterbox.ui.screen

import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.example.chatterbox.utils.CharacterManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
    // boolean 값을 이용해 boolean 값이 변경되면 동작하도록 설정해보았음. ui 함수를 분리했는데 콜백함수를 사용하지 않아서 였음.
    val messageList by db.messageDao().getMessagesByThreadId(threadId)
        .collectAsState(emptyList())

    val mAuth = FirebaseAuth.getInstance()
    val currentUid = mAuth.currentUser?.uid!!
    val assistantId = newAssistantId.removePrefix(currentUid)
    var userText: String by remember { mutableStateOf("") }
    val characterNameFromAssistantId =
        CharacterManager.getAllCharacters().find { it.assistantId == assistantId }?.characterName
            ?: ""
    var isResponse: Boolean by remember { mutableStateOf(false) }
    var isEnabled: Boolean by remember { mutableStateOf(false) }
    LaunchedEffect(userText) {
        isEnabled = userText.isNotBlank() && !isResponse
    }
    val scrollState = rememberLazyListState()
    if (messageList.isNotEmpty()) {
        LaunchedEffect(messageList.lastIndex) {
            scrollState.scrollToItem(index = messageList.lastIndex)
        }
    }
    // 뒤로 가기 버튼 클릭시 답변 생성 중이라면 dialog로 경고 표시
    // 뒤로가기 버튼 이벤트 감지
    var showDialog by remember { mutableStateOf(false) }
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    DisposableEffect(showDialog) {
        val callback = backDispatcher?.addCallback {
            if (isResponse) {
                showDialog = true
            } else {
                navController.navigateUp()
            }
        }
        onDispose {
            callback?.remove()
        }
    }
    if (showDialog) {
        AlertDialog(
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White,
            icon = { Icons.Default.Warning },
            iconContentColor = Color.Yellow,
            onDismissRequest = { /* 아무 작업 없음 */ },
            title = { Text("경고") },
            text = { Text("답변이 아직 생성되지 않았습니다.\n나가시겠습니까?") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color(0xFF22ABF3)),
                    onClick = {
                        navController.navigateUp()
                        showDialog = false
                    }
                ) {
                    Text("나가기")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(contentColor = Color(0xFF22ABF3), containerColor = Color.White),
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("취소")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6F2FF))
    ) {
        Column {
            TopBar(navController, characterNameFromAssistantId, isResponse) {
                showDialog = true
            }
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
                                append(characterNameFromAssistantId)
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
                                val createUserMesAt =
                                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                                        Date()
                                    )
                                val userMessage =
                                    Message(
                                        threadId = threadId,
                                        userRole = true,
                                        senderName = characterNameFromAssistantId,
                                        content = userText,
                                        createAt = createUserMesAt
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
                                val createChatMesAt =
                                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                                        Date()
                                    )
                                val chatMessage = Message(
                                    threadId = threadId,
                                    userRole = false,
                                    senderName = characterNameFromAssistantId,
                                    content = textContext.text.value,
                                    createAt = createChatMesAt
                                )
                                db.messageDao().insertMessageAll(chatMessage)
                                isResponse = false
                            }
                        },
                        enabled = isEnabled
                    ) {
                        Icon(imageVector = Icons.Filled.Send, contentDescription = "send")
                    }
                })
//            TextFieldComponent(assistantId, threadId, isResponse, hasReload)
        }
    }
}

@Composable
fun TopBar(
    navController: NavHostController,
    characterNameFromAssistantId: String,
    isResponse: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = {
            if (isResponse) {
                onClick()
            } else {
                navController.navigateUp()
            }
        }) {
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
            .fillMaxWidth(),
        horizontalAlignment = if (message.userRole == true) Alignment.End else Alignment.Start,
    ) {
        if (message.userRole == true) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Bottom)
                ) {
                    Text(
                        text = message.createAt ?: "",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .background(
                            Color(0xFF2FCC59),
                            RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    message.content?.let {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
        if (message.userRole == false) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(
                            Color(0xFF22ABF3),
                            RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    message.content?.let {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp),
                            textAlign = TextAlign.Start
                        )
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Bottom)
                ) {
                    Text(
                        text = message.createAt ?: "",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingMessageWithAnimation() {
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