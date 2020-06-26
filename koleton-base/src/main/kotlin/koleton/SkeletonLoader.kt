@file:Suppress("FunctionName", "NOTHING_TO_INLINE", "unused")
@file:OptIn(ExperimentalKoletonApi::class)

package koleton

import android.content.Context
import android.view.View
import koleton.annotation.ExperimentalKoletonApi
import koleton.custom.KoletonView
import koleton.skeleton.Skeleton
import koleton.target.Target

interface SkeletonLoader {

    companion object {
        /** Alias for [SkeletonLoaderBuilder]. */
        @JvmStatic
        @JvmName("builder")
        inline fun Builder(context: Context) =
            SkeletonLoaderBuilder(context)

        /** Create a new [SkeletonLoader] without configuration. */
        @JvmStatic
        @JvmName("create")
        inline operator fun invoke(context: Context) = SkeletonLoaderBuilder(context).build()
    }

    /**
     * The default options that are used to fill in unset [Skeleton] values.
     */
    val defaults: DefaultSkeletonOptions

    /**
     * Launch an asynchronous operation that loads the [Skeleton] and sets the result on its [Target].
     *
     * @param skeleton The skeleton to load.
     */
    fun load(skeleton: Skeleton)

    fun generate(skeleton: Skeleton): KoletonView

    /**
     * Hide and cancel any skeleton attached to [view]}.
     *
     * @param view The original view
     * @param koletonView The skeleton view loaded
     */
    fun hide(view: View, koletonView: KoletonView)

}
