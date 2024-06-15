plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.google.proto)
}

android {
    namespace = "com.kt.apps.media.football"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kt.apps.media.football"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
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
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.naigation.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.jsoup)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.gson)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.cronet.api)
    implementation(libs.cronet.okhttp)
    implementation(libs.cronet.play.services.provider)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.perf)
    implementation(libs.firebase.remote.config)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.crashlytics)
    implementation(libs.media3.common)
    implementation(libs.media3.ui)
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.exoplayer.hls)
    implementation(libs.media3.effect)
    implementation(libs.media3.session)
    implementation(libs.media3.transformer)
    implementation(libs.media3.datasource.cronet)

    implementation(libs.data.store.preferences)
    implementation(libs.data.store)

    implementation(libs.dagger.hilt)
    implementation(libs.dagger.hilt.nav)
    kapt(libs.dagger.hilt.compiler)

    implementation(project(":sharedutils"))
    implementation(project(":API"))
    implementation(project(":football"))
    implementation(project(":core"))
    implementation(project(":core:models"))
    implementation(project(":player"))
    implementation("com.github.fengdai.compose:media:+")

}