package com.binbon.app.presentation.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import com.binbon.app.domain.model.Video
import com.binbon.app.presentation.feed.components.VideoFeedList
import com.binbon.app.ui.theme.BinbonTheme
import com.binbon.app.ui.theme.Dimens

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FeedScreenContent(
        uiState = uiState,
        player = viewModel.playerManager.player,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun FeedScreenContent(
    uiState: FeedUiState,
    player: Player?,
    onIntent: (FeedIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 3.dp
                )
            }

            uiState.error != null -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(Dimens.paddingLarge),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.ErrorOutline,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(Dimens.paddingMedium))
                    Text(
                        text = uiState.error,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(Dimens.paddingMedium))
                    Button(
                        onClick = { onIntent(FeedIntent.LoadFeed) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Retry",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            uiState.videos.isNotEmpty() -> {
                VideoFeedList(
                    videos = uiState.videos,
                    player = player,
                    isPlaying = uiState.isPlaying,
                    onVideoChanged = { index ->
                        onIntent(FeedIntent.OnVideoChanged(index))
                    },
                    onTogglePlayPause = {
                        onIntent(FeedIntent.TogglePlayPause)
                    },
                    onLikeClick = { videoId ->
                        onIntent(FeedIntent.OnLikeClicked(videoId))
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    BinbonTheme {
        FeedScreenContent(
            uiState = FeedUiState(
                videos = listOf(
                    Video(
                        id = 1,
                        videoUrl = "",
                        username = "adventure_seeker",
                        description = "Blazing through the weekend 🔥 #adventure #fire",
                        likes = 14200,
                        comments = 892,
                        shares = 340,
                        isLiked = false
                    ),
                    Video(
                        id = 2,
                        videoUrl = "",
                        username = "travel_vibes",
                        description = "When you need a bigger escape ✈️ #travel #explore",
                        likes = 28500,
                        comments = 1540,
                        shares = 720,
                        isLiked = true
                    )
                )
            ),
            player = null,
            onIntent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FeedScreenLoadingPreview() {
    BinbonTheme {
        FeedScreenContent(
            uiState = FeedUiState(isLoading = true),
            player = null,
            onIntent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FeedScreenErrorPreview() {
    BinbonTheme {
        FeedScreenContent(
            uiState = FeedUiState(error = "Unable to load feed. Please check your connection."),
            player = null,
            onIntent = {}
        )
    }
}
