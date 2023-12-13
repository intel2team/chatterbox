package com.example.chatterbox.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalComposeUiApi::class, BetaOpenAI::class)
@Composable
fun ChatScreen(navController: NavHostController, assistantId: String) {
    val context = LocalContext.current
    val db = remember {
        AppDatabase.getDatabase(context)
    }
    val threadId =
        db.threadDao().getThreadIdByAssistantId(assistantId).collectAsState(initial = "").value
    val messageList by db.messageDao().getMessagesByThreadId(threadId).collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    Text(text = "chat")
    val apiKey = BuildConfig.OPENAI_API_KEY
    val openAI = OpenAI(
        token = apiKey,
        timeout = Timeout(socket = 60.seconds)
    )
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
    var userText: String by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()
//    LaunchedEffect(messageList) {
//        scrollState.scrollToItem(index = messageList.lastIndex)
//    }
    val characterNameFromAssistantId = Character.values().find { it.assistantId == assistantId }?.characterName
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6F2FF))
    ) {
        Column {
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
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(value = userText, onValueChange = { userText = it },
                    placeholder = { Text("${characterNameFromAssistantId}와 대화해 보세요!") })
                IconButton(onClick = {
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
                            Message(threadId = threadId, userRole = true, content = userText)
                        db.messageDao().insertMessageAll(userMessage)
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
                    }
                }) {
                    Icon(imageVector = Icons.Filled.Send, contentDescription = "send")
                }
            }
        }
    }
}

@Composable
fun MessageContent(message: Message) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(if (message.userRole) Color(0xFF2FCC59) else Color(0xFF22ABF3))
            .clip(MaterialTheme.shapes.medium)
            .padding(8.dp)
    ) {
        Text(
            text = message.content,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = if (message.userRole) TextAlign.End else TextAlign.Start
        )
    }
}

