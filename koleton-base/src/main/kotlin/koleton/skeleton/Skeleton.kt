package koleton.skeleton

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import koleton.target.Target

/**
 * The base class for a view skeleton.
 *
 * There are two types of view skeletons: [ViewSkeleton]s.
 */
sealed class Skeleton {

    abstract val context: Context
    abstract val target: Target?
    abstract val colorResId: Int
    abstract val borderRadiusId: Int
    abstract val isShimmerEnabled: Boolean
    abstract val shimmerColorResId: Int
    abstract val shimmerDuration: Int
    abstract val shimmerDirection: Int
    abstract val shimmerTilt: Int
    abstract val lifecycle: Lifecycle?

    /**
     * A set of callbacks for a [Skeleton].
     */
    interface Listener {

        /**
         * Called immediately after [Target.onStart].
         */
        @MainThread
        fun onStart(skeleton: Skeleton) {}

        /**
         * Called if the skeleton completes successfully.
         */
        @MainThread
        fun onSuccess(skeleton: Skeleton) {}

        /**
         * Called if the skeleton is cancelled.
         */
        @MainThread
        fun onCancel(skeleton: Skeleton) {}

        /**
         * Called if an error occurs while executing the skeleton.
         */
        @MainThread
        fun onError(skeleton: Skeleton, throwable: Throwable) {}
    }
}

class ViewSkeleton internal constructor(
    override val context: Context,
    override val target: Target?,
    override val lifecycle: Lifecycle?,
    @ColorRes override val colorResId: Int,
    @DimenRes override val borderRadiusId: Int,
    override val isShimmerEnabled: Boolean,
    @ColorRes override val shimmerColorResId: Int,
    override val shimmerDuration: Int,
    override val shimmerDirection: Int,
    override val shimmerTilt: Int,
    internal val backgroundResId: Int,
    internal val backgroundDrawable: Drawable?,
    internal val isLineSkeletonEnabled: Boolean
) : Skeleton() {

    companion object {
        /** Alias for [ViewSkeletonBuilder]. */
        @JvmStatic
        @JvmName("builder")
        inline fun Builder(context: Context) = ViewSkeletonBuilder(context)

        /** Alias for [ViewSkeletonBuilder]. */
        @JvmStatic
        @JvmOverloads
        @JvmName("builder")
        inline fun Builder(
            skeleton: ViewSkeleton,
            context: Context = skeleton.context
        ) = ViewSkeletonBuilder(skeleton, context)
    }

    /** Create a new [ViewSkeletonBuilder] instance using this as a base. */
    @JvmOverloads
    fun newBuilder(context: Context = this.context) = ViewSkeletonBuilder(this, context)
}