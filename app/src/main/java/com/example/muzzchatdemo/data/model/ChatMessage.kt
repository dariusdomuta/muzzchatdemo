package com.example.muzzchatdemo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatMessage (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "author_id") val authorId: Int,
    @ColumnInfo(name = "timestamp")val timestamp: Long,
    @ColumnInfo(name = "message")val message: String,
)
