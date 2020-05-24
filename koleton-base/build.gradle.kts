import Dependencies.androidAppCompat
import Dependencies.androidConstraintLayout
import Dependencies.androidKtx
import Dependencies.junit
import Dependencies.kotlinCoroutines
import Dependencies.lifecycle
import Dependencies.shimmer
import koleton.compileSdk
import koleton.minSdk
import koleton.targetSdk
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(project.compileSdk)

    defaultConfig {
        minSdkVersion(project.minSdk)
        targetSdkVersion(project.targetSdk)
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    libraryVariants.all {
        generateBuildConfigProvider?.configure { enabled = false }
    }
}

dependencies {
    //Android
    implementation(androidAppCompat)
    implementation(androidKtx)
    implementation(androidConstraintLayout)
    implementation(lifecycle)

    // Kotlin
    api(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    api(kotlinCoroutines)

    //Shimmer
    implementation(shimmer)

    // Tests
    testImplementation(junit)
}