package com.example.whatsapp.presentation.chat_box

import com.example.whatsapp.R

data class ChatListModel(
    val image: Int,     // Resource ID of drawable (e.g., R.drawable.profile)
    val name: String,   // Contact name
    val time: String,   // Message time (e.g., "10:30 AM")
    val message: String // Last message text
)
