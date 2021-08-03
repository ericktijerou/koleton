import com.android.build.gradle.BaseExtension
import koleton.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm")
    `maven-publish`
    id(Release.Nexus.plugin) version Release.Nexus.version
    id("org.jetbrains.dokka") version "1.5.0" apply false
}


buildscript {
    apply(from = "buildSrc/extra.gradle.kts")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(rootProject.extra["androidPlugin"].toString())
        classpath(rootProject.extra["kotlinPlugin"].toString())
        classpath(Dependencies.safeArgsPlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

apply(from ="${rootDir}/scripts/publish-root.gradle")

val androidModules = listOf("koleton-singleton", "koleton-base")
val androidSampleModules = listOf("koleton-sample")

subprojects {

    val isAndroidModule = project.name in androidModules
    val isSample = project.name in androidSampleModules
    val isJvmModule = !isAndroidModule && !isSample

    if (isJvmModule) {
        apply {
            plugin(Kotlin.plugin)
        }

        configure<JavaPluginConvention> {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8

            sourceSets {
                getByName("main").java.srcDirs("src/main/kotlin")
                getByName("test").java.srcDirs("src/test/kotlin")
                getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
            }
        }

        val sourcesJar by tasks.registering(Jar::class) {
            from(sourceSets["main"].allSource)
            archiveClassifier.set("sources")
        }

        val doc by tasks.creating(Javadoc::class) {
            isFailOnError = false
            source = sourceSets["main"].allJava
        }
    }

    if (isAndroidModule) {
        apply {
            plugin(Android.libPlugin)
            plugin(Kotlin.androidPlugin)
        }

        configure<BaseExtension> {
            compileSdkVersion(project.compileSdk)

            defaultConfig {
                minSdkVersion(project.minSdk)
                targetSdkVersion(project.targetSdk)
                versionCode = 1
                versionName = project.publishVersion
            }

            sourceSets {
                getByName("main").java.srcDirs("src/main/kotlin")
                getByName("test").java.srcDirs("src/test/kotlin")
                getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    consumerProguardFiles("proguard-rules.pro")
                }
            }

            lintOptions {
                isAbortOnError = false
            }

            testOptions {
                unitTests.isReturnDefaultValues = true
            }
        }

        if (!isSample) {
            version = project.publishVersion
            group = project.groupId
            val artifactName = if (project.name == Koleton.mainModule) Koleton.name else project.name

            apply {
                plugin(Release.MavenPublish.plugin)
                plugin("org.jetbrains.dokka")
            }

            tasks.withType<KotlinCompile> {
                kotlinOptions {
                    freeCompilerArgs = listOf("-progressive", "-Xopt-in=kotlin.RequiresOptIn")
                    jvmTarget = JavaVersion.VERSION_1_8.toString()
                }
            }

            dependencies {
                implementation(Kotlin.stdlib)
            }

            ext {
                set("PUBLISH_GROUP_ID", project.groupId)
                set("PUBLISH_VERSION", project.publishVersion)
                set("PUBLISH_ARTIFACT_ID", artifactName)
            }

            apply(from = "${rootProject.projectDir}/scripts/publish-module.gradle")

        }
    }
}