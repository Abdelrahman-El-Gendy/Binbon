plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)   // Required for Nav3 @Serializable routes
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.binbon.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.binbon.app"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // ── Core ──────────────────────────────────────────────────────────────
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.splashscreen)

    // ── Compose BOM (manages all compose versions consistently) ───────────
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.bundles.compose.core)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // ── Navigation 3 ──────────────────────────────────────────────────────
    implementation(libs.bundles.navigation3)
    // OR individually if you don't need adaptive pane:
    // implementation(libs.androidx.navigation3.runtime)
    // implementation(libs.androidx.navigation3.ui)
    // implementation(libs.androidx.navigation3.viewmodel)

    // ── Lifecycle / ViewModel ─────────────────────────────────────────────
    implementation(libs.bundles.lifecycle)

    // ── Hilt ──────────────────────────────────────────────────────────────
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    // ── Media3 ExoPlayer ─────────────────────────────────────────────────
    implementation(libs.bundles.media3)

    // ── Networking ───────────────────────────────────────────────────────
    implementation(libs.bundles.networking)

    // ── Image Loading ─────────────────────────────────────────────────────
    implementation(libs.coil.compose)
    implementation(libs.coil.video)     // Thumbnail from video frame

    // ── Serialization (required for Nav3 @Serializable route keys) ────────
    implementation(libs.bundles.serialization)

    // ── Coroutines ────────────────────────────────────────────────────────
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // ── Unit Testing ─────────────────────────────────────────────────────
    testImplementation(libs.bundles.testing.unit)

    // ── Android / UI Testing ─────────────────────────────────────────────
    androidTestImplementation(libs.bundles.testing.android)
}