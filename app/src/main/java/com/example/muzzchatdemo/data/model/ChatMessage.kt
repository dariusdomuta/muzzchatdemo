package com.example.muzzchatdemo.data.model

data class ChatMessage (
    val id: Int,
    val authorId: Int,
    val timestamp: Long,
    val message: String,
)
