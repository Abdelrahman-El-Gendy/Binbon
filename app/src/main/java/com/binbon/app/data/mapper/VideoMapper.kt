package com.binbon.app.data.mapper

import com.binbon.app.data.remote.dto.VideoDto
import com.binbon.app.domain.model.Video

fun VideoDto.toDomain(): Video {
    return Video(
        id = id,
        videoUrl = videoUrl,
        username = username,
        description = description,
        likes = likes ?: 0,
        comments = comments ?: 0,
        shares = shares ?: 0,
        userAvatarUrl = userAvatarUrl ?: ""
    )
}

fun List<VideoDto>.toDomain(): List<Video> = map { it.toDomain() }
