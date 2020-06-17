import com.android.build.gradle.BaseExtension
import koleton.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version Kotlin.version apply false
    `maven-publish`
    id(Release.Bintray.plugin) version Release.Bintray.version
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
    }
    dependencies {
        classpath(Dependencies.safeArgsPlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

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
            plugin(Kotlin.androidExtensionsPlugin)
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

            val sourcesJar by tasks.registering(Jar::class) {
                from(sourceSets["main"].java.srcDirs)
                archiveClassifier.set("sources")
            }

            val doc by tasks.creating(Javadoc::class) {
                isFailOnError = false
                source = sourceSets["main"].java.sourceFiles
                classpath += files(bootClasspath.joinToString(File.pathSeparator))
                classpath.plus(configurations["compile"])
            }
        }

        if (!isSample) {
            version = project.publishVersion
            group = project.groupId
            val artifactName = if (project.name == Koleton.mainModule) Koleton.name else project.name

            apply {
                plugin(Release.MavenPublish.plugin)
                plugin(Release.Bintray.plugin)
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

            bintray {
                user = findProperty("bintrayUser") as? String
                key = findProperty("bintrayKey") as? String
                publish = !project.publishVersion.endsWith("SNAPSHOT")
                setPublications(artifactName)
                with(pkg) {
                    repo = Koleton.repository
                    name = artifactName
                    desc = Koleton.description
                    githubRepo = project.vcsUrl
                    userOrg = Developer.id
                    websiteUrl = project.vcsUrl
                    vcsUrl = project.vcsUrl
                    issueTrackerUrl = project.issueTrackerUrl
                    setLicenses(Koleton.licenseName)
                    with(version) {
                        name = project.publishVersion
                        vcsTag = "v${project.publishVersion}"
                    }
                }
            }

            fun org.gradle.api.publish.maven.MavenPom.addDependencies() = withXml {
                asNode().appendNode("dependencies").let { depNode ->
                    configurations.implementation.get().allDependencies.forEach {
                        depNode.appendNode("dependency").apply {
                            appendNode("groupId", it.group)
                            appendNode("artifactId", it.name)
                            appendNode("version", it.version)
                        }
                    }
                }
            }

            val javadocJar by tasks.creating(Jar::class) {
                val doc by tasks
                dependsOn(doc)
                from(doc)
                archiveClassifier.set("javadoc")
            }

            val sourcesJar by tasks
            publishing {
                publications {
                    register(artifactName, MavenPublication::class) {
                        if (project.hasProperty("android")) {
                            artifact("$buildDir/outputs/aar/${project.name}-release.aar") {
                                builtBy(tasks.getByPath("assemble"))
                            }
                        } else {
                            from(components["java"])
                        }
                        groupId = project.groupId
                        artifactId = artifactName
                        version = project.publishVersion
                        artifact(sourcesJar)
                        artifact(javadocJar)
                        pom {
                            name.set(artifactName)
                            description.set(Koleton.description)
                            url.set(project.vcsUrl)
                            scm { url.set(project.vcsUrl) }
                            issueManagement { url.set(project.issueTrackerUrl) }
                            licenses {
                                license {
                                    name.set(Koleton.licenseName)
                                    url.set(Koleton.licenseUrl)
                                }
                            }
                            developers {
                                developer {
                                    id.set(Developer.id)
                                    name.set(Developer.name)
                                }
                            }
                        }
                        if (project.hasProperty("android")) {
                            pom.addDependencies()
                        }
                    }
                }
            }
        }
    }
}

