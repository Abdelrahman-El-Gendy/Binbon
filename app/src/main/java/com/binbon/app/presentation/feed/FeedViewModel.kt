package com.binbon.app.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binbon.app.core.network.NetworkResult
import com.binbon.app.core.player.ExoPlayerManager
import com.binbon.app.domain.usecase.GetVideoFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getVideoFeedUseCase: GetVideoFeedUseCase,
    val playerManager: ExoPlayerManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        handleIntent(FeedIntent.LoadFeed)
    }

    fun handleIntent(intent: FeedIntent) {
        when (intent) {
            is FeedIntent.LoadFeed -> loadFeed()
            is FeedIntent.OnVideoChanged -> onVideoChanged(intent.index)
            is FeedIntent.TogglePlayPause -> togglePlayPause()
            is FeedIntent.OnLikeClicked -> onLikeClicked(intent.videoId)
        }
    }

    private fun loadFeed() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getVideoFeedUseCase()) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            videos = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    // Auto-play the first video
                    if (result.data.isNotEmpty()) {
                        playerManager.play(result.data[0].videoUrl)
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun onVideoChanged(index: Int) {
        val videos = _uiState.value.videos
        if (index in videos.indices) {
            _uiState.update {
                it.copy(currentVideoIndex = index, isPlaying = true)
            }
            playerManager.play(videos[index].videoUrl)
        }
    }

    private fun togglePlayPause() {
        playerManager.togglePlayPause()
        _uiState.update {
            it.copy(isPlaying = playerManager.isPlaying)
        }
    }

    private fun onLikeClicked(videoId: Int) {
        _uiState.update { state ->
            state.copy(
                videos = state.videos.map { video ->
                    if (video.id == videoId) {
                        video.copy(
                            isLiked = !video.isLiked,
                            likes = if (video.isLiked) video.likes - 1 else video.likes + 1
                        )
                    } else video
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        playerManager.release()
    }
}
