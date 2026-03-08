package com.binbon.app.presentation.feed.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import com.binbon.app.core.extensions.noRippleClickable
import com.binbon.app.domain.model.Video
import com.binbon.app.ui.theme.Dimens
import kotlinx.coroutines.delay

@Composable
fun VideoFeedItem(
    video: Video,
    player: Player,
    isPlaying: Boolean,
    isCurrentPage: Boolean,
    onTogglePlayPause: () -> Unit,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showPauseIcon by remember { mutableStateOf(false) }

    // Auto-hide pause icon after 1 second
    LaunchedEffect(showPauseIcon) {
        if (showPauseIcon) {
            delay(1000L)
            showPauseIcon = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .noRippleClickable {
                onTogglePlayPause()
                showPauseIcon = true
            }
    ) {
        // Video Player Surface — only attach player to the currently visible page
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    useController = false
                    setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                }
            },
            update = { playerView ->
                // Only bind the player to the page that is currently settled/visible
                playerView.player = if (isCurrentPage) player else null
            },
            modifier = Modifier.fillMaxSize()
        )

        // Bottom gradient scrim for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
                        )
                    )
                )
        )

        // Play/Pause indicator
        AnimatedVisibility(
            visible = showPauseIcon,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.4f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(Dimens.iconSizeLarge)
                )
            }
        }

        // Action buttons (right side)
        VideoActionButtons(
            likes = video.likes,
            comments = video.comments,
            shares = video.shares,
            isLiked = video.isLiked,
            onLikeClick = onLikeClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = Dimens.paddingMedium, bottom = 100.dp)
        )

        // Metadata overlay (bottom-left)
        VideoMetadataOverlay(
            video = video,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = Dimens.paddingMedium, bottom = 100.dp, end = 80.dp)
        )
    }
}
