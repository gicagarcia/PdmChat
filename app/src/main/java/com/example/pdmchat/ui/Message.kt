package com.example.pdmchat.ui

data class Message(
    val senderId: String = "",
    val messageText: String = "",
    val timestamp: Long = 0
)
