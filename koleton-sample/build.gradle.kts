import Dependencies.androidAppCompat
import Dependencies.androidConstraintLayout
import Dependencies.androidCore
import Dependencies.androidKtx
import Dependencies.junit
import Dependencies.kotlinStdLib
import Dependencies.materialDesign
import Dependencies.navigationFragment
import Dependencies.navigationUi
import Dependencies.shimmer

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        applicationId = "koleton.sample"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            isTestCoverageEnabled = true
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            isZipAlignEnabled = true
            proguardFile(getDefaultProguardFile("proguard-android.txt"))
            proguardFile(file("proguard-rules.pro"))
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }
}

kapt {
    useBuildCache = true
}

dependencies {
    //Libraries
    implementation(project(":koleton-singleton"))

    //Android
    implementation(androidAppCompat)
    implementation(androidCore)
    implementation(androidKtx)
    implementation(androidConstraintLayout)
    implementation(navigationFragment)
    implementation(navigationUi)

    // Google Material Design
    implementation(materialDesign)

    // Kotlin
    implementation(kotlinStdLib)

    //Shimmer
    implementation(shimmer)

    // Tests
    testImplementation(junit)
}