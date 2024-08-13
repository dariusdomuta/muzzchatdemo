package com.example.muzzchatdemo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.muzzchatdemo.data.model.ChatMessage

@Database(entities = [ChatMessage::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun chatMessagesDao(): ChatMessageDao
}
