@file:Suppress("unused")

package koleton

import org.gradle.api.Project

val Project.minSdk: Int
    get() = intProperty("minSdk")

val Project.targetSdk: Int
    get() = intProperty("targetSdk")

val Project.compileSdk: Int
    get() = intProperty("compileSdk")

private fun Project.intProperty(name: String): Int {
    return (property(name) as String).toInt()
}