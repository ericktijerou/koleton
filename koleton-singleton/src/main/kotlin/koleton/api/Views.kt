@file:JvmName("Views")
@file:Suppress("unused")
@file:OptIn(ExperimentalKoletonApi::class)

package koleton.api

import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import koleton.Koleton
import koleton.SkeletonLoader
import koleton.annotation.ExperimentalKoletonApi
import koleton.custom.KoletonView
import koleton.skeleton.RecyclerViewSkeleton
import koleton.skeleton.TextViewSkeleton
import koleton.skeleton.ViewSkeleton
import koleton.util.KoletonUtils

/**
 * This is the type-unsafe version of [View.loadSkeleton].
 *
 * Example:
 * ```
 * view.loadSkeleton {
 *      color(R.color.colorExample)
 *      ...
 * }
 * ```
 *
 * @param skeletonLoader The [SkeletonLoader] that will be used to create the [ViewSkeleton].
 * @param builder An optional lambda to configure the skeleton before it is loaded.
 */
@JvmSynthetic
inline fun View.loadSkeleton(
    skeletonLoader: SkeletonLoader = Koleton.skeletonLoader(context),
    builder: ViewSkeleton.Builder.() -> Unit = {}
) {
    val skeleton = ViewSkeleton.Builder(context)
        .target(this)
        .apply(builder)
        .build()
    skeletonLoader.load(skeleton)
}

/**
 * This is the type-unsafe version of [TextView.loadSkeleton].
 *
 * Example:
 * ```
 * textView?.loadSkeleton(length = 10) {
 *      color(R.color.colorExample)
 *      ...
 * }
 * ```
 *
 * @param skeletonLoader The [SkeletonLoader] that will be used to create the [TextViewSkeleton].
 * @param builder An optional lambda to configure the skeleton before it is loaded.
 */
@JvmSynthetic
inline fun TextView.loadSkeleton(
    length: Int,
    skeletonLoader: SkeletonLoader = Koleton.skeletonLoader(context),
    builder: TextViewSkeleton.Builder.() -> Unit = {}
) {
    val skeleton = TextViewSkeleton.Builder(context, length)
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
 *      ...
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
    builder: ViewSkeleton.Builder.() -> Unit = {}
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
 *      ...
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
    builder: RecyclerViewSkeleton.Builder.() -> Unit = {}
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
 * Calls the specified function [block] after the skeleton is hidden.
 */
fun View.afterHideSkeleton(block: () -> Unit) {
    KoletonUtils.afterHide(this, block)
}

/**
 * Hide all skeletons associated with this [View].
 */
fun View.hideSkeleton() {
    KoletonUtils.hide(this)
}