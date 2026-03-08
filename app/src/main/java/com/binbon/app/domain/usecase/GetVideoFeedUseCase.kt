package com.binbon.app.domain.usecase

import com.binbon.app.core.network.NetworkResult
import com.binbon.app.domain.model.Video
import com.binbon.app.domain.repository.VideoRepository
import javax.inject.Inject

class GetVideoFeedUseCase @Inject constructor(
    private val repository: VideoRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Video>> {
        return repository.getVideoFeed()
    }
}
