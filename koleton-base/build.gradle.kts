dependencies {
    //Android
    implementation(Dependencies.androidAppCompat)
    implementation(Dependencies.androidKtx)
    implementation(Dependencies.androidConstraintLayout)
    implementation(Dependencies.lifecycle)
    implementation(Dependencies.androidRecyclerView)

    api(Dependencies.kotlinCoroutines)

    //Shimmer
    implementation(Dependencies.shimmer)

    // Tests
    testImplementation(Dependencies.junit)
}