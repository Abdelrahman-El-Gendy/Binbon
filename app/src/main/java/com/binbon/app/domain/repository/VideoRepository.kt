package com.binbon.app.domain.repository

import com.binbon.app.core.network.NetworkResult
import com.binbon.app.domain.model.Video

interface VideoRepository {
    suspend fun getVideoFeed(): NetworkResult<List<Video>>
}
