package com.example.chatterbox.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.chatterbox.data.local.entity.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface MessageDao {
    @Insert
    fun insertMessageAll(vararg message: Message)

    @Query("SELECT * FROM message WHERE threadId = :threadId")
    fun getMessagesByThreadId(threadId: String): Flow<List<Message>>
}
