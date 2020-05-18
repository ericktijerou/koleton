object Dependencies {
    const val androidAppCompat = "androidx.appcompat:appcompat:${Versions.androidAppCompat}"
    const val androidConstraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.androidConstraintLayout}"
    const val androidCore = "androidx.core:core:${Versions.androidCore}"
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val junit = "junit:junit:${Versions.junit}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinStdLib}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinStdLib}"
    const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    const val androidKtx = "androidx.core:core-ktx:${Versions.androidKtxVersion}"
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmerVersion}"
    const val lifecycle = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"
    const val compatibilityValidator = "org.jetbrains.kotlinx:binary-compatibility-validator:${Versions.validatorVersion}"
}

object Versions {
    internal const val androidAppCompat = "1.0.2"
    internal const val androidConstraintLayout = "2.0.0-beta5"
    internal const val androidCore = "1.0.2"
    internal const val androidGradlePlugin = "3.6.3"
    internal const val junit = "4.12"
    internal const val kotlinStdLib = "1.3.72"
    internal const val materialDesign = "1.0.0"
    internal const val androidKtxVersion = "0.1"
    internal const val shimmerVersion = "0.5.0"
    internal const val lifecycleVersion = "2.2.0"
    internal const val validatorVersion = "0.2.3"
}