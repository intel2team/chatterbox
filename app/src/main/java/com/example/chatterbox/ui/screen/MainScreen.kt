package com.example.chatterbox.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.client.OpenAI
import com.example.chatterbox.BuildConfig
import com.example.chatterbox.data.local.AppDatabase
import com.example.chatterbox.data.local.entity.Assistant
import com.example.chatterbox.data.local.entity.Thread
import com.example.chatterbox.ui.navigation.Screen
import kotlinx.coroutines.launch

enum class Character(val characterName: String, val assistantId: String) {
    OLAF("올라프", "asst_pKRMmMds8HVj6cWNmhhjZxGh"),
    CHARACTER_TWO("Character Two", "2"),
    CHARACTER_THREE("Character Three", "3"),
    CHARACTER_FOUR("Character Four", "4"),
    CHARACTER_FIVE("Character Five", "5")
}

@OptIn(BetaOpenAI::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = remember {
        AppDatabase.getDatabase(context)
    }
    val assistantList = db.assistantDao().getAssistantAll().collectAsState(initial = emptyList()).value
    val scope = rememberCoroutineScope()
    Column {
        Row {
            Button(onClick = {
                val newAssistantId = Assistant(Character.OLAF.assistantId)
                for(assistantId in assistantList) {
                    if(newAssistantId != assistantId) {
                        val openAI = OpenAI(BuildConfig.OPENAI_API_KEY)
                        db.assistantDao().insertAssistantAll(newAssistantId)
                        scope.launch {
                            val threadId = openAI.thread().id.id
                            val thread = Thread(threadId, newAssistantId.assistantId)
                            db.threadDao().insertThreadAll(thread)
                        }
                    }
                }
                navController.navigate(Screen.Chat.route + "/" + newAssistantId.assistantId)
            }) {
                Text(text = "올라프")
            }
            Button(onClick = { navController.navigate(Screen.Chat.route) }) {
                Text(text = "다른 캐릭")
            }
            Button(onClick = { navController.navigate(Screen.Chat.route) }) {
                Text(text = "다른 캐릭")
            }
            Button(onClick = { navController.navigate(Screen.Chat.route) }) {
                Text(text = "다른 캐릭")
            }
        }
        Button(onClick = {
            navController.navigate(Screen.Profile.route) {
                popUpTo(Screen.Main.route)
            }
        }) {
            Text(text = "프로필")
        }
    }
}

