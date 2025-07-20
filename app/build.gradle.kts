plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.2.0"
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.deepakjetpackcompose.crowtradingapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.deepakjetpackcompose.crowtradingapp"
        minSdk = 26
        targetSdk = 35
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
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    kapt("com.google.dagger:hilt-android-compiler:2.56.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    //coil
    implementation("io.coil-kt.coil3:coil-compose:3.2.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.2.0")

    //navigation
    val nav_version = "2.9.1"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")


    //ktor client
    implementation("io.ktor:ktor-client-core:2.3.6")
    implementation("io.ktor:ktor-client-android:2.3.6") // Ktor Client for Android
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6") // JSON Serialization (for handling JSON responses)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.6")// Content Negotiation (for automatic serialization)
    implementation("io.ktor:ktor-client-logging:2.3.6")// Logging (for debugging API requests and responses)
    implementation("org.slf4j:slf4j-simple:2.0.9")// SLF4J Logger (for better logging support)

    //lottie animation
    implementation ("com.airbnb.android:lottie-compose:6.4.0")

    //constraint layout
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    //MpAndroidChart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
   //swipe tp refresh
    implementation("com.google.accompanist:accompanist-swiperefresh:0.31.5-beta")



}