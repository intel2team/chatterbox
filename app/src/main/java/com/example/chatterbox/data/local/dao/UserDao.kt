package com.example.chatterbox.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chatterbox.data.local.entity.Assistant
import com.example.chatterbox.data.local.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUser(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(vararg user: User)
}