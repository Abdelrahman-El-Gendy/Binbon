package com.binbon.app.presentation.feed

sealed interface FeedIntent {
    data object LoadFeed : FeedIntent
    data class OnVideoChanged(val index: Int) : FeedIntent
    data object TogglePlayPause : FeedIntent
    data class OnLikeClicked(val videoId: Int) : FeedIntent
}
