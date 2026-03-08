package com.binbon.app.core.player

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExoPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var _player: ExoPlayer? = null
    private var currentUrl: String? = null

    val player: ExoPlayer
        get() = _player ?: createPlayer().also { _player = it }

    @OptIn(UnstableApi::class)
    private fun createPlayer(): ExoPlayer {
        return ExoPlayer.Builder(context)
            .build()
            .apply {
                repeatMode = Player.REPEAT_MODE_ONE
                playWhenReady = true
            }
    }

    fun play(url: String) {
        // Skip if already playing this exact URL
        if (url == currentUrl && _player?.isPlaying == true) return

        currentUrl = url
        player.apply {
            stop()
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            playWhenReady = true
        }
    }

    fun pause() {
        _player?.pause()
    }

    fun resume() {
        _player?.play()
    }

    fun togglePlayPause() {
        _player?.let {
            if (it.isPlaying) it.pause() else it.play()
        }
    }

    val isPlaying: Boolean
        get() = _player?.isPlaying ?: false

    fun release() {
        _player?.release()
        _player = null
        currentUrl = null
    }
}
