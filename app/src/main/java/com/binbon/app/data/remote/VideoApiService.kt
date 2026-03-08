package com.binbon.app.data.remote

import com.binbon.app.data.remote.dto.VideoDto
import retrofit2.http.GET

interface VideoApiService {

    @GET("videos")
    suspend fun getVideoFeed(): List<VideoDto>
}
