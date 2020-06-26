@file:JvmName("Views")
@file:Suppress("unused")
@file:OptIn(ExperimentalKoletonApi::class)

package koleton.api

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import koleton.Koleton
import koleton.SkeletonLoader
import koleton.annotation.ExperimentalKoletonApi
import koleton.custom.KoletonView
import koleton.skeleton.RecyclerViewSkeleton
import koleton.skeleton.RecyclerViewSkeletonBuilder
import koleton.skeleton.ViewSkeleton
import koleton.skeleton.ViewSkeletonBuilder
import koleton.util.KoletonUtils

/**
 * This is the type-unsafe version of [View.loadSkeleton].
 *
 * Example:
 * ```
 * view.loadSkeleton {
 *      color(R.color.colorExample)
 * }
 * ```
 *
 * @param skeletonLoader The [SkeletonLoader] that will be used to create the [ViewSkeleton].
 * @param builder An optional lambda to configure the skeleton before it is loaded.
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
    skeletonLoader.load(skeleton)
}


/**
 * This is the type-unsafe version of [View.generateSkeleton].
 *
 * Example:
 * ```
 * val koletonView = view.generateSkeleton {
 *      color(R.color.colorSkeleton)
 * }
 * koletonView.showSkeleton()
 * ```
 *
 * @param skeletonLoader The [SkeletonLoader] that will be used to create the [ViewSkeleton].
 * @param builder An optional lambda to configure the skeleton.
 * @return the [KoletonView] that contains the skeleton
 */
@JvmSynthetic
inline fun View.generateSkeleton(
    skeletonLoader: SkeletonLoader = Koleton.skeletonLoader(context),
    builder: ViewSkeletonBuilder.() -> Unit = {}
): KoletonView {
    val skeleton = ViewSkeleton.Builder(context)
        .target(this)
        .apply(builder)
        .build()
    return skeletonLoader.generate(skeleton)
}

/**
 * Load the skeleton referenced by [itemLayout] and set it on this [RecyclerView].
 *
 * This is the type-unsafe version of [RecyclerView.loadSkeleton].
 *
 * Example:
 * ```
 * recyclerView.loadSkeleton(R.layout.item_example) {
 *      color(R.color.colorExample)
 * }
 * ```
 * @param itemLayout Layout resource of the itemView that will be used to create the skeleton view.
 * @param skeletonLoader The [SkeletonLoader] that will be used to create the [RecyclerViewSkeleton].
 * @param builder An optional lambda to configure the skeleton before it is loaded.
 */
@JvmSynthetic
inline fun RecyclerView.loadSkeleton(
    @LayoutRes itemLayout: Int,
    skeletonLoader: SkeletonLoader = Koleton.skeletonLoader(context),
    builder: RecyclerViewSkeletonBuilder.() -> Unit = {}
) {
    val skeleton = RecyclerViewSkeleton.Builder(context, itemLayout)
        .target(this)
        .apply(builder)
        .build()
    skeletonLoader.load(skeleton)
}

/**
 * @return True if the skeleton associated with this [View] is shown.
 */
fun View.isSkeletonShown(): Boolean {
    return KoletonUtils.isSkeletonShown(this)
}

/**
 * Hide all skeletons associated with this [View].
 */
fun View.hideSkeleton() {
    KoletonUtils.hide(this)
}