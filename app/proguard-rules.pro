# ════════════════════════════════════════════════════════════════════════════
#  Binbon — ProGuard / R8 rules
#  Applied only to release builds (isMinifyEnabled = true)
# ════════════════════════════════════════════════════════════════════════════

# ─── Debug info (keep for readable crash traces in release) ─────────────────
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile


# ════════════════════════════════════════════════════════════════════════════
#  Kotlin
# ════════════════════════════════════════════════════════════════════════════

# Keep Kotlin metadata so reflection-based APIs (serialization, etc.) work
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod

# Kotlin companion objects and top-level functions
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-dontwarn kotlinx.**

# Kotlin coroutines — internal debug infrastructure only needed in debug
-dontwarn kotlinx.coroutines.debug.*


# ════════════════════════════════════════════════════════════════════════════
#  Kotlin Serialization
# ════════════════════════════════════════════════════════════════════════════

# Keep the serializer companion on every @Serializable class
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}
-keepclasseswithmembers class * {
    kotlinx.serialization.KSerializer serializer(...);
}
# Preserve generated serializers (named *$serializer)
-keep class **$$serializer { *; }
-keepclassmembers @kotlinx.serialization.Serializable class ** {
    *** Companion;
    *** INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}


# ════════════════════════════════════════════════════════════════════════════
#  Hilt / Dagger
# ════════════════════════════════════════════════════════════════════════════

# Dagger generated components
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.HiltAndroidApp
-keepclasseswithmembers class * {
    @dagger.hilt.android.AndroidEntryPoint *;
}
-keepclasseswithmembers class * {
    @dagger.* <fields>;
    @dagger.* <methods>;
}
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
-dontwarn dagger.**


# ════════════════════════════════════════════════════════════════════════════
#  Retrofit 2
# ════════════════════════════════════════════════════════════════════════════

# Keep Retrofit interfaces and their annotations intact
-keep,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}
-keep class retrofit2.** { *; }
-keepattributes Exceptions
-dontwarn retrofit2.**
# Required when using Kotlin suspend functions with Retrofit
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}


# ════════════════════════════════════════════════════════════════════════════
#  OkHttp 3 / 4
# ════════════════════════════════════════════════════════════════════════════

-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
# JSR 305 annotations used by OkHttp
-dontwarn javax.annotation.**


# ════════════════════════════════════════════════════════════════════════════
#  Gson
# ════════════════════════════════════════════════════════════════════════════

-keep class com.google.gson.** { *; }
-keepattributes *Annotation*
# Keep all data classes / model classes that Gson deserializes.
# Replace this package with wherever your data models live once you add them.
-keep class com.binbon.app.data.model.** { *; }
-keep class com.binbon.app.domain.model.** { *; }
# Prevent stripping fields annotated with @SerializedName
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-dontwarn com.google.gson.**


# ════════════════════════════════════════════════════════════════════════════
#  Coil 2
# ════════════════════════════════════════════════════════════════════════════

-keep class coil.** { *; }
-dontwarn coil.**


# ════════════════════════════════════════════════════════════════════════════
#  Media3 / ExoPlayer
# ═════════════════════════════���══════════════════════════════════════════════

-keep class androidx.media3.** { *; }
-dontwarn androidx.media3.**
# ExoPlayer native extractors — must not be renamed
-keep class com.google.android.exoplayer2.** { *; }
-dontwarn com.google.android.exoplayer2.**


# ════════════════════════════════════════════════════════════════════════════
#  Navigation 3
# ════════════════════════════════════════════════════════════════════════════

-keep class androidx.navigation3.** { *; }
-dontwarn androidx.navigation3.**
# Adaptive navigation
-keep class androidx.compose.material3.adaptive.** { *; }
-dontwarn androidx.compose.material3.adaptive.**


# ════════════════════════════════════════════════════════════════════════════
#  Jetpack Compose
# ════════════════════════════════════════════════════════════════════════════

# Compose relies heavily on reflection for Previews (debug only, but keep safe)
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**


# ════════════════════════════════════════════════════════════════════════════
#  Lifecycle / ViewModel
# ════════════════════════════════════════════════════════════════════════════

-keep class androidx.lifecycle.** { *; }
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>();
    <init>(androidx.lifecycle.SavedStateHandle);
    <init>(...);
}
-keepclassmembers class * implements androidx.lifecycle.LifecycleObserver {
    <methods>;
}
-dontwarn androidx.lifecycle.**


# ════════════════════════════════════════════════════════════════════════════
#  AndroidX Core / Splash Screen
# ════════════════════════════════════════════════════════════════════════════

-dontwarn androidx.core.**
-dontwarn androidx.activity.**


# ════════════════════════════════════════════════════════════════════════════
#  General Android safety
# ════════════════════════════════════════════════════════════════════════════

# Keep all custom Application, Activity, Service, etc. entry-points
-keep public class * extends android.app.Application
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Parcelable CREATOR fields must not be renamed
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# Enum values() / valueOf() are called reflectively by Android
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
