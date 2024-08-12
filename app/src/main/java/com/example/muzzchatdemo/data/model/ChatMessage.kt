package com.example.muzzchatdemo.data.model

data class ChatMessage (
    val id: Int,
    val author: String,
    val timestamp: Long,
    val message: String
)
