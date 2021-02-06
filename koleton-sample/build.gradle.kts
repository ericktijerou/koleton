import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Android.appPlugin)
    id(Kotlin.androidPlugin)
    id("androidx.navigation.safeargs.kotlin")
}

dependencies {
    //Libraries
    implementation(project(":koleton-singleton"))

    //Android
    implementation(Dependencies.androidAppCompat)
    implementation(Dependencies.androidCore)
    implementation(Dependencies.androidKtx)
    implementation(Dependencies.androidConstraintLayout)
    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.navigationUi)
    implementation(Dependencies.paging)
    implementation(Dependencies.lifecycle)
    implementation(Dependencies.liveDataKtx)

    // Google Material Design
    implementation(Dependencies.materialDesign)

    // Kotlin
    implementation(Kotlin.stdlib)

    //Shimmer
    implementation(Dependencies.shimmer)

    // Tests
    testImplementation(Dependencies.junit)
}

configure<BaseExtension> {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "koleton.sample"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-progressive", "-Xopt-in=kotlin.RequiresOptIn")
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
    }

    android.buildFeatures.viewBinding = true
}