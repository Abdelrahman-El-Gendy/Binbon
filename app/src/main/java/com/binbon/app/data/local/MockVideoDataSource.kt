package com.binbon.app.data.local

import com.binbon.app.data.remote.dto.VideoDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockVideoDataSource @Inject constructor() {

    fun getVideoFeed(): List<VideoDto> {
        return listOf(
            VideoDto(
                id = 1,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                username = "adventure_seeker",
                description = "Blazing through the weekend 🔥 #adventure #fire",
                likes = 14200,
                comments = 892,
                shares = 340,
                userAvatarUrl = ""
            ),
            VideoDto(
                id = 2,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                username = "travel_vibes",
                description = "When you need a bigger escape ✈️ #travel #explore",
                likes = 28500,
                comments = 1540,
                shares = 720,
                userAvatarUrl = ""
            ),
            VideoDto(
                id = 3,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                username = "fun_factory",
                description = "Life is short, have bigger fun! 🎉 #fun #vibes",
                likes = 9300,
                comments = 430,
                shares = 210,
                userAvatarUrl = ""
            ),
            VideoDto(
                id = 4,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
                username = "speed_demon",
                description = "Joyrides that make your heart race 🏎️ #cars #speed",
                likes = 45600,
                comments = 2100,
                shares = 980,
                userAvatarUrl = ""
            ),
            VideoDto(
                id = 5,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
                username = "drama_queen",
                description = "Total meltdown energy 😤💥 #drama #mood",
                likes = 7800,
                comments = 560,
                shares = 150,
                userAvatarUrl = ""
            ),
            VideoDto(
                id = 6,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
                username = "offroad_king",
                description = "Street to dirt, no limits 🚗💨 #offroad #subaru",
                likes = 33100,
                comments = 1870,
                shares = 640,
                userAvatarUrl = ""
            ),
            VideoDto(
                id = 7,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                username = "cinema_buff",
                description = "Open source cinema at its finest 🎬 #film #blender",
                likes = 52400,
                comments = 3200,
                shares = 1500,
                userAvatarUrl = ""
            )
        )
    }
}
