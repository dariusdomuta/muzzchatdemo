package com.example.muzzchatdemo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.muzzchatdemo.data.model.ChatMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {

    @Query("SELECT * FROM ChatMessage")
    fun getAll(): Flow<List<ChatMessage>>

    @Insert
    suspend fun insertMessages(vararg messages: ChatMessage)
}
