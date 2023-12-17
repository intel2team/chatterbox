package com.example.chatterbox.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "message",
    foreignKeys = [
        ForeignKey(
            entity = Thread::class,
            parentColumns = ["threadId"],
            childColumns = ["threadId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Message(
    @PrimaryKey(autoGenerate = true) val messageId: Long = 0,
    val threadId: String,
    val userRole: Boolean? = null,
    val senderName: String,
    val content: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val createAt: String? = null
)
