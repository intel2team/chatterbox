package com.example.chatterbox.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "thread",
    foreignKeys = [
        ForeignKey(
            entity = Assistant::class,
            parentColumns = ["assistantId"],
            childColumns = ["assistantId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Thread(
    @PrimaryKey val threadId: String,
    val assistantId: String,
)