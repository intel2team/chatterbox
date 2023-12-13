package com.example.chatterbox.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.chatterbox.data.local.entity.Thread
import kotlinx.coroutines.flow.Flow

// 지금은 그냥 넘어가지만 Thread가 java.lang 에도 있기 때문에 오해 안생기게 naming 하기..
@Dao
interface ThreadDao {
    @Insert
    fun insertThreadAll(vararg thread: Thread)

    @Query("SELECT threadId FROM thread WHERE assistantId = :assistantId")
    fun getThreadIdByAssistantId(assistantId: String): Flow<String>
}