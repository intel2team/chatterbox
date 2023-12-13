package com.example.chatterbox.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chatterbox.data.local.dao.AssistantDao
import com.example.chatterbox.data.local.dao.MessageDao
import com.example.chatterbox.data.local.dao.ThreadDao
import com.example.chatterbox.data.local.dao.UserDao
import com.example.chatterbox.data.local.entity.Assistant
import com.example.chatterbox.data.local.entity.Message
import com.example.chatterbox.data.local.entity.Thread
import com.example.chatterbox.data.local.entity.User

@Database(
    entities = [User::class, Assistant::class, Message::class, Thread::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun assistantDao(): AssistantDao
    abstract fun threadDao(): ThreadDao
    abstract fun messageDao(): MessageDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
               Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "chat.db"
                ).build()
            }
        }
    }
}