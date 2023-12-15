package com.example.chatterbox.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.chatterbox.data.local.entity.Assistant
import kotlinx.coroutines.flow.Flow

@Dao
interface AssistantDao {
    @Insert
    fun insertAssistantAll(vararg assistant: Assistant)

    @Query("SELECT assistantId FROM assistant WHERE uid = :uid")
    fun getAssistantsByUid(uid: String): Flow<List<String>>

    @Delete
    fun deleteAssistant(assistant: Assistant)
}