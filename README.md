# Binbon - TikTok-Style Video Feed App

Binbon is a modern Android application showcasing a TikTok-style vertical video feed. It is built entirely with **Jetpack Compose** and follows **Clean Architecture** principles combined with the **MVI (Model-View-Intent)** presentation pattern.

## 📱 Features

- **TikTok-style Vertical Swipe**: Smooth scrolling through videos using Compose's native `VerticalPager`.
- **Media Playback**: Robust video streaming leveraging **Media3 / ExoPlayer**, optimized with a singleton layer to save memory and handle fast scrolling.
- **Interactive UI**: Animated action buttons (like with spring bounce physics), play/pause overlays, and bottom gradient scrims for maximum text readability.
- **Type-safe Navigation**: Uses the latest **Navigation 3** library with Kotlin serialization for strongly-typed routing.
- **Dependency Injection**: Fully wired with **Dagger Hilt**.
- **Network Ready**: Configured with Retrofit and OkHttp, currently fueled by a `MockVideoDataSource` with real sample MP4s that stream instantly.

## 🛠 Tech Stack

- **UI**: Jetpack Compose
- **Architecture**: Clean Architecture & MVI (Model-View-Intent)
- **Dependency Injection**: Dagger Hilt
- **Navigation**: Jetpack Navigation 3 (`androidx.navigation3`)
- **Media**: Media3 ExoPlayer (`androidx.media3`)
- **Networking**: Retrofit2 & OkHttp3
- **Image Loading**: Coil (Compose & Video Frames)
- **Asynchrony**: Kotlin Coroutines & Flow

## 🏗 Architecture Layout

The application is structured into the following layers:

- `domain`: Contains enterprise rules, models (`Video`), use cases (`GetVideoFeedUseCase`), and repository interfaces. Pure Kotlin, no Android dependencies.
- `data`: Contains implementations of repositories, Retrofit API services, DTOs, mappers, and Data Sources.
- `presentation`: Contains the UI written in Compose, ViewModels scoped via Hilt, and the `FeedIntent`/`FeedUiState` defining the MVI contract.
- `core`: Contains app-wide utility classes like `NetworkResult` and `ExoPlayerManager`.
- `navigation`: Defines the `@Serializable` NavKeys and sets up the `NavDisplay`.
- `di`: Hilt modules for providing singletons (`AppModule`, `PlayerModule`).

## 🚀 Getting Started

### Prerequisites

- Android Studio Koala (or newer recommended)
- JDK 17
- Minimum SDK 26 (Android 8.0)
- Target SDK 36

### Current Data Source (Mock)

By default, the application runs using `MockVideoDataSource`, which provides 7 real MP4 video links hosted on Google's public sample GTV bucket. This ensures the app builds and works straight out of the box without needing a backend.

### Switching to a Real API

When you are ready to connect to your own backend:

1. Update the base URL in `di/AppModule.kt`:
   ```kotlin
   @Provides
   @Singleton
   fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
       return Retrofit.Builder()
           .baseUrl("https://your-real-api.com/") 
           // ...
   }
   ```
2. Update the `VideoRepositoryImpl` to consume the real API instead of the mock data source:
   ```kotlin
   @Singleton
   class VideoRepositoryImpl @Inject constructor(
       private val apiService: VideoApiService // Inject this instead
   ) : VideoRepository {
       override suspend fun getVideoFeed(): NetworkResult<List<Video>> {
           return try {
               val videos = apiService.getVideoFeed().toDomain()
               NetworkResult.Success(videos)
           } catch (e: Exception) {
               NetworkResult.Error(e.message ?: "Error")
           }
       }
   }
   ```

## 📝 License

This project is for educational and portfolio purposes.
