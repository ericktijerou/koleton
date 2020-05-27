@file:JvmName("Views")
@file:Suppress("unused")
@file:OptIn(ExperimentalKoletonApi::class)

package koleton.api

import android.view.View
import android.widget.TextView
import koleton.Koleton
import koleton.SkeletonLoader
import koleton.annotation.ExperimentalKoletonApi
import koleton.skeleton.ViewSkeleton
import koleton.skeleton.ViewSkeletonBuilder
import koleton.util.KoletonUtils

/**
 * This is the type-unsafe version of [View.loadSkeleton].
 *
 * TODO: Add example
 *
 * @param skeletonLoader The [SkeletonLoader] that will be used to create and launch the [ViewSkeleton].
 * @param builder An optional lambda to configure the skeleton before it is launched.
 */
@JvmSynthetic
inline fun View.loadSkeleton(
    skeletonLoader: SkeletonLoader = Koleton.skeletonLoader(context),
    builder: ViewSkeletonBuilder.() -> Unit = {}
) {
    val skeleton = ViewSkeleton.Builder(context)
        .target(this)
        .apply(builder)
        .build()
    skeletonLoader.execute(skeleton)
}

@JvmSynthetic
inline fun View.hideSkeleton() {
    KoletonUtils.hide(this)
}