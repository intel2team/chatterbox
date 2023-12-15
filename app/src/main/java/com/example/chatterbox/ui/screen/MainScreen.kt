package com.example.chatterbox.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
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
    val openAI = OpenAI(BuildConfig.OPENAI_API_KEY)
    val mAuth =  FirebaseAuth.getInstance()
    val currentUid = mAuth.currentUser?.uid ?: ""
    val assistants =
        db.assistantDao().getAssistantsByUid(currentUid).collectAsState(initial = emptyList()).value

    val scope = rememberCoroutineScope()
    Column {
        Row {
            Button(onClick = {
                val newAssistantId = currentUid + Character.OLAF.assistantId
                if (newAssistantId !in assistants) {
                    val newAssistant = Assistant(newAssistantId, currentUid)
                    scope.launch(Dispatchers.IO) {
                        db.assistantDao().insertAssistantAll(newAssistant)
                        val thread = openAI.thread()
                        val threadId = thread.id.id
                        val thread2 = Thread(threadId, newAssistant.assistantId)
                        db.threadDao().insertThreadAll(thread2)
                    }
                }
                navController.navigate(Screen.Chat.route + "/${newAssistantId}")
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

