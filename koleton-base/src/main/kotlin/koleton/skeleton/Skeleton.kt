@file:Suppress("FunctionName", "NOTHING_TO_INLINE", "unused")

package koleton.skeleton

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.annotation.Px
import androidx.lifecycle.Lifecycle
import com.facebook.shimmer.Shimmer
import koleton.target.Target

/**
 * The base class for a skeleton view.
 *
 * There are two types of skeleton views: [ViewSkeleton]s and [RecyclerViewSkeleton]s.
 */
sealed class Skeleton {

    abstract val context: Context
    abstract val target: Target?
    abstract val colorResId: Int?
    abstract val cornerRadius: Int?
    abstract val isShimmerEnabled: Boolean?
    abstract val lifecycle: Lifecycle?
    abstract val shimmer: Shimmer?

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
    @ColorRes override val colorResId: Int?,
    @Px override val cornerRadius: Int?,
    override val isShimmerEnabled: Boolean?,
    override val shimmer: Shimmer?
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

class RecyclerViewSkeleton internal constructor(
    override val context: Context,
    override val target: Target?,
    override val lifecycle: Lifecycle?,
    @ColorRes override val colorResId: Int?,
    @Px override val cornerRadius: Int?,
    override val isShimmerEnabled: Boolean?,
    @LayoutRes internal val itemLayoutResId: Int,
    internal val itemCount: Int?,
    override val shimmer: Shimmer?
) : Skeleton() {

    companion object {
        /** Alias for [RecyclerViewSkeletonBuilder]. */
        @JvmStatic
        @JvmName("builder")
        inline fun Builder(context: Context, @LayoutRes itemLayout: Int) = RecyclerViewSkeletonBuilder(context, itemLayout)

        /** Alias for [RecyclerViewSkeletonBuilder]. */
        @JvmStatic
        @JvmOverloads
        @JvmName("builder")
        inline fun Builder(
            skeleton: RecyclerViewSkeleton,
            context: Context = skeleton.context
        ) = RecyclerViewSkeletonBuilder(skeleton, context)
    }

    /** Create a new [RecyclerViewSkeletonBuilder] instance using this as a base. */
    @JvmOverloads
    fun newBuilder(context: Context = this.context) = RecyclerViewSkeletonBuilder(this, context)
}