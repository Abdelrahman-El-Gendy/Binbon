package com.binbon.app.presentation.feed

import com.binbon.app.domain.model.Video

data class FeedUiState(
    val videos: List<Video> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentVideoIndex: Int = 0,
    val isPlaying: Boolean = true
)
