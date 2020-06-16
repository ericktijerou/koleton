@file:Suppress("unused")

package koleton

import org.gradle.api.Project

val Project.minSdk: Int
    get() = intProperty("minSdk")

val Project.targetSdk: Int
    get() = intProperty("targetSdk")

val Project.compileSdk: Int
    get() = intProperty("compileSdk")

val Project.groupId: String
    get() = stringProperty("groupId")

val Project.publishVersion: String
    get() = stringProperty("publishVersion")

val Project.vcsUrl: String
    get() = stringProperty("vcsUrl")

val Project.issueTrackerUrl: String
    get() = stringProperty("issueTrackerUrl")

private fun Project.intProperty(name: String): Int {
    return (property(name) as String).toInt()
}

private fun Project.stringProperty(name: String): String {
    return property(name) as String
}