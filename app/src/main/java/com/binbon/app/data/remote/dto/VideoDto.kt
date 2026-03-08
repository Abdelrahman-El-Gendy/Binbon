package com.binbon.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("video_url")
    val videoUrl: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("likes")
    val likes: Int? = null,

    @SerializedName("comments")
    val comments: Int? = null,

    @SerializedName("shares")
    val shares: Int? = null,

    @SerializedName("user_avatar_url")
    val userAvatarUrl: String? = null
)
