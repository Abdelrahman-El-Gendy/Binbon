package com.binbon.app.presentation.feed.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.media3.common.Player
import com.binbon.app.domain.model.Video
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun VideoFeedList(
    videos: List<Video>,
    player: Player?,
    isPlaying: Boolean,
    onVideoChanged: (Int) -> Unit,
    onTogglePlayPause: () -> Unit,
    onLikeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { videos.size }
    )

    // Listen for page changes to switch video
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .collect { page ->
                onVideoChanged(page)
            }
    }

    VerticalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize(),
        beyondViewportPageCount = 1
    ) { page ->
        val isCurrentPage = pagerState.settledPage == page
        VideoFeedItem(
            video = videos[page],
            player = player,
            isPlaying = isPlaying && isCurrentPage,
            isCurrentPage = isCurrentPage,
            onTogglePlayPause = onTogglePlayPause,
            onLikeClick = { onLikeClick(videos[page].id) }
        )
    }
}
