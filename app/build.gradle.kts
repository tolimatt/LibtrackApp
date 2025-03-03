plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.libtrack"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.libtrack"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.retrofit)
    implementation(libs.converter.scalars)
    implementation(libs.okhttp)

    implementation (libs.gson)

    implementation (libs.kotlinx.coroutines.android)

    implementation(libs.androidx.lifecycle.runtime.ktx.v240)
    implementation(libs.androidx.activity.compose.v131)

    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose.v160)

    implementation (libs.converter.gson) // Or latest version
    implementation (libs.logging.interceptor) // For logging (optional, but recommended)

    implementation (libs.androidx.room.runtime)
    annotationProcessor (libs.androidx.room.compiler) // Use kapt if you use Kotlin
    implementation (libs.androidx.room.ktx) // for Kotlin extensions

    // Jetpack Compose dependencies (if not already added)
    implementation (libs.material3)
    implementation (libs.ui.tooling.preview)

    // Coroutines
    implementation (libs.kotlinx.coroutines.android)

    implementation (libs.androidx.lifecycle.runtime.ktx.v260)
    implementation (libs.okhttp.v493)
    implementation (libs.logging.interceptor)

    implementation (libs.coil.compose)
    
    implementation (libs.androidx.lifecycle.livedata.ktx.v260)  // LiveData for Kotlin extensions
    implementation (libs.coil.compose)  // Correct version for Coil

    implementation(libs.gson)



}