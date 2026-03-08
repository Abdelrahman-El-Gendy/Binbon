package com.binbon.app.data.repository

import com.binbon.app.core.network.NetworkResult
import com.binbon.app.data.local.MockVideoDataSource
import com.binbon.app.data.mapper.toDomain
import com.binbon.app.domain.model.Video
import com.binbon.app.domain.repository.VideoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepositoryImpl @Inject constructor(
    private val mockDataSource: MockVideoDataSource
) : VideoRepository {

    override suspend fun getVideoFeed(): NetworkResult<List<Video>> {
        return try {
            val videos = mockDataSource.getVideoFeed().toDomain()
            NetworkResult.Success(videos)
        } catch (e: Exception) {
            NetworkResult.Error(
                message = e.message ?: "An unexpected error occurred"
            )
        }
    }
}
