package com.example.chatterbox.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
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
import com.example.chatterbox.data.local.entity.Assistant
import com.example.chatterbox.data.local.entity.Message
import com.example.chatterbox.data.local.entity.Thread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalComposeUiApi::class, BetaOpenAI::class)
@Composable
fun ChatScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = remember {
        AppDatabase.getDatabase(context)
    }
    val assistant = Assistant("asst_pKRMmMds8HVj6cWNmhhjZxGh")
    val thread = Thread("thread_DAfbzsiCCUzN0m6tHg0TNyuC", "asst_pKRMmMds8HVj6cWNmhhjZxGh")
    val assistantList = db.assistantDao().getAssistantAll().collectAsState(initial = emptyList()).value
    for(a in assistantList) {
        if(assistant != a) {
            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    db.assistantDao().insertAssistantAll(assistant)
                    db.threadDao().insertThreadAll(thread)
                }
            }
        }
    }
    val messageList by db.messageDao().getMessageAll().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    Text(text = "chat")
    val apiKey = BuildConfig.OPENAI_API_KEY
    val openAI = OpenAI(
        token = apiKey,
        timeout = Timeout(socket = 60.seconds)
    )
//    var assistant: Assistant?
//    LaunchedEffect(Unit) {
//        assistant = openAI.assistant(id = AssistantId("asst_pKRMmMds8HVj6cWNmhhjZxGh"))
////        아래 코드로 assistant 수정 가능
////        assistant = openAI.assistant(id = AssistantId("asst_pKRMmMds8HVj6cWNmhhjZxGh"),
////            request = AssistantRequest(
////                instructions = "수정 instruction",
////                tools = listOf(AssistantTool.RetrievalTool), // tool 추가 가능
////                model = ModelId("gpt-4"), // 모델 변경
////                fileIds = listOf(FileId("file-abc123"), FileId("file-abc123")), // 파일 추가가능
////            )
////        )
////        만약 사용자가 thread id 없으면 생성해주어야 함.
////        val thread = openAI.thread()
//    }

    var userText: String by remember { mutableStateOf("") }


    Column {
        LazyColumn {
            items(messageList) { message ->
                MessageContent(message)
            }
        }
        TextField(value = userText, onValueChange = { userText = it} )
        IconButton(onClick = {
            scope.launch(Dispatchers.IO) {
                val message = openAI.message(
                    threadId = ThreadId("thread_DAfbzsiCCUzN0m6tHg0TNyuC"),
                    request = MessageRequest(
                        role = Role.User,
                        content = userText
                    )
                )
                // userText db 추가
                val userMessage = Message(threadId = "thread_DAfbzsiCCUzN0m6tHg0TNyuC", userRole = true, content = userText)
                db.messageDao().insertMessageAll(userMessage)
                userText = ""
                val run = openAI.createRun(
                    threadId = ThreadId("thread_DAfbzsiCCUzN0m6tHg0TNyuC"),
                    request = RunRequest(assistantId = AssistantId("asst_pKRMmMds8HVj6cWNmhhjZxGh")),
                )
                do {
                    delay(1500)
                    val retrievedRun = openAI.getRun(threadId = ThreadId("thread_DAfbzsiCCUzN0m6tHg0TNyuC"), runId = run.id)
                } while (retrievedRun.status != Status.Completed)
                val assistantMessages = openAI.messages(ThreadId("thread_DAfbzsiCCUzN0m6tHg0TNyuC"))
                val textContext = assistantMessages[0].content.first() as MessageContent.Text
                // textContext.value db 추가
                val chatMessage = Message(threadId = "thread_DAfbzsiCCUzN0m6tHg0TNyuC", userRole = false, content = textContext.text.value)
                db.messageDao().insertMessageAll(chatMessage)
            }
        }) {
            Icon(imageVector = Icons.Filled.Send, contentDescription = "send")
        }
    }
}

@Composable
fun MessageContent(message: Message) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(if (message.userRole) Color.Green else Color.Blue)
            .clip(MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = message.content,
            color = Color.White,
            textAlign = if (message.userRole) TextAlign.End else TextAlign.Start
        )
    }
}

