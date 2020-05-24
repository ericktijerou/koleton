package koleton

import android.content.Context
import koleton.skeleton.Skeleton
import koleton.skeleton.ViewSkeleton
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
        inline operator fun invoke(context: Context) = SkeletonLoaderBuilder(
            context
        ).build()
    }

    /**
     * The default options that are used to fill in unset [Skeleton] values.
     */
    val defaults: DefaultSkeletonOptions

    /**
     * Launch an asynchronous operation that executes the [ViewSkeleton] and sets the result on its [Target].
     *
     * @param skeleton The skeleton to execute.
     */
    fun execute(skeleton: Skeleton)

    /**
     * Launch an asynchronous operation that executes the [ViewSkeleton] and sets the result on its [Target].
     *
     * @param skeleton The skeleton to execute.
     */
    fun hide(target: Target?, skeletonId: Int)

}
