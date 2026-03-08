package com.binbon.app.domain.model

data class Video(
    val id: Int,
    val videoUrl: String,
    val username: String,
    val description: String,
    val likes: Int = 0,
    val comments: Int = 0,
    val shares: Int = 0,
    val isLiked: Boolean = false,
    val userAvatarUrl: String = ""
)
