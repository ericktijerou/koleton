@file:Suppress("unused")

package koleton.skeleton

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.annotation.Px
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer
import koleton.custom.KoletonView
import koleton.target.RecyclerViewTarget
import koleton.target.SimpleViewTarget
import koleton.target.Target
import koleton.target.TextViewTarget

/**
 * The base class for a skeleton view.
 *
 * There are two types of skeleton views: [ViewSkeleton]s and [RecyclerViewSkeleton]s.
 */
sealed class Skeleton {

    abstract val context: Context
    abstract val target: Target?
    abstract val colorResId: Int?
    abstract val cornerRadius: Float?
    abstract val isShimmerEnabled: Boolean?
    abstract val lifecycle: Lifecycle?
    abstract val shimmer: Shimmer?
    abstract val lineSpacing: Float?

    /**
     * A set of callbacks for a [Skeleton].
     */
    interface Listener {

        /**
         * Called immediately after [Target.onStart].
         */
        @MainThread
        fun onStart(skeleton: Skeleton) {
        }

        /**
         * Called if the skeleton completes successfully.
         */
        @MainThread
        fun onSuccess(skeleton: Skeleton) {
        }

        /**
         * Called if the skeleton is cancelled.
         */
        @MainThread
        fun onCancel(skeleton: Skeleton) {
        }

        /**
         * Called if an error occurs while executing the skeleton.
         */
        @MainThread
        fun onError(skeleton: Skeleton, throwable: Throwable) {
        }
    }
}

class ViewSkeleton internal constructor(
    override val context: Context,
    override val target: Target?,
    override val lifecycle: Lifecycle?,
    @ColorRes override val colorResId: Int?,
    @Px override val cornerRadius: Float?,
    override val isShimmerEnabled: Boolean?,
    override val shimmer: Shimmer?,
    override val lineSpacing: Float?
) : Skeleton() {

    /** Create a new [Builder] instance using this as a base. */
    @JvmOverloads
    fun newBuilder(context: Context = this.context) = Builder(this, context)

    class Builder : SkeletonBuilder<Builder> {

        private var target: Target?
        private var lifecycle: Lifecycle?

        constructor(context: Context) : super(context) {
            target = null
            lifecycle = null
        }

        @JvmOverloads
        constructor(
            skeleton: ViewSkeleton,
            context: Context = skeleton.context
        ) : super(skeleton, context) {
            target = skeleton.target
            lifecycle = skeleton.lifecycle
        }

        /**
         * Convenience function to set [view] as the [Target].
         */
        fun target(view: View) = apply {
            target(SimpleViewTarget(view))
        }

        /**
         * Convenience function to create and set the [Target].
         */
        inline fun target(
            crossinline onStart: () -> Unit = {},
            crossinline onError: () -> Unit = {},
            crossinline onSuccess: (skeleton: KoletonView) -> Unit = {}
        ) = target(object : Target {
            override fun onStart() = onStart()
            override fun onError() = onError()
            override fun onSuccess(skeleton: KoletonView) = onSuccess(skeleton)
        })

        fun target(target: Target?) = apply {
            this.target = target
        }

        fun lifecycle(lifecycle: Lifecycle?) = apply {
            this.lifecycle = lifecycle
        }

        /**
         * Create a new [ViewSkeleton] instance.
         */
        fun build(): ViewSkeleton {
            return ViewSkeleton(
                context,
                target,
                lifecycle,
                colorResId,
                cornerRadius,
                isShimmerEnabled,
                shimmer,
                lineSpacing
            )
        }
    }
}

class RecyclerViewSkeleton internal constructor(
    override val context: Context,
    override val target: Target?,
    override val lifecycle: Lifecycle?,
    @ColorRes override val colorResId: Int?,
    @Px override val cornerRadius: Float?,
    override val isShimmerEnabled: Boolean?,
    @LayoutRes internal val itemLayoutResId: Int,
    internal val itemCount: Int?,
    override val shimmer: Shimmer?,
    override val lineSpacing: Float?
) : Skeleton() {

    /** Create a new [Builder] instance using this as a base. */
    @JvmOverloads
    fun newBuilder(context: Context = this.context) = Builder(this, context)

    class Builder : SkeletonBuilder<Builder> {

        private var target: Target?
        private var lifecycle: Lifecycle?
        @LayoutRes
        private var itemLayoutResId: Int
        private var itemCount: Int?

        constructor(context: Context, @LayoutRes itemLayout: Int) : super(context) {
            target = null
            lifecycle = null
            itemLayoutResId = itemLayout
            itemCount = null
        }

        @JvmOverloads
        constructor(
            skeleton: RecyclerViewSkeleton,
            context: Context = skeleton.context
        ) : super(skeleton, context) {
            target = skeleton.target
            lifecycle = skeleton.lifecycle
            itemLayoutResId = skeleton.itemLayoutResId
            itemCount = skeleton.itemCount
        }

        /**
         * Convenience function to set [recyclerView] as the [Target].
         */
        fun target(recyclerView: RecyclerView) = apply {
            target(RecyclerViewTarget(recyclerView))
        }

        /**
         * Convenience function to create and set the [Target].
         */
        inline fun target(
            crossinline onStart: () -> Unit = {},
            crossinline onError: () -> Unit = {},
            crossinline onSuccess: (skeleton: KoletonView) -> Unit = {}
        ) = target(object : Target {
            override fun onStart() = onStart()
            override fun onError() = onError()
            override fun onSuccess(skeleton: KoletonView) = onSuccess(skeleton)
        })

        fun target(target: Target?) = apply {
            this.target = target
        }

        /**
         * The total number of items in the skeleton adapter.
         */
        fun itemCount(itemCount: Int) = apply {
            this.itemCount = itemCount
        }

        fun lifecycle(lifecycle: Lifecycle?) = apply {
            this.lifecycle = lifecycle
        }

        /**
         * Create a new [ViewSkeleton] instance.
         */
        fun build(): RecyclerViewSkeleton {
            return RecyclerViewSkeleton(
                context,
                target,
                lifecycle,
                colorResId,
                cornerRadius,
                isShimmerEnabled,
                itemLayoutResId,
                itemCount,
                shimmer,
                lineSpacing
            )
        }
    }
}

class TextViewSkeleton internal constructor(
        override val context: Context,
        override val target: Target?,
        override val lifecycle: Lifecycle?,
        @ColorRes override val colorResId: Int?,
        @Px override val cornerRadius: Float?,
        override val isShimmerEnabled: Boolean?,
        override val shimmer: Shimmer?,
        override val lineSpacing: Float?,
        internal val length: Int
) : Skeleton() {

    /** Create a new [Builder] instance using this as a base. */
    @JvmOverloads
    fun newBuilder(context: Context = this.context) = Builder(this, context)

    class Builder : SkeletonBuilder<Builder> {

        private var target: Target?
        private var lifecycle: Lifecycle?
        private var length: Int

        constructor(context: Context, length: Int) : super(context) {
            target = null
            lifecycle = null
            this.length = length
        }

        @JvmOverloads
        constructor(
                skeleton: TextViewSkeleton,
                context: Context = skeleton.context
        ) : super(skeleton, context) {
            target = skeleton.target
            lifecycle = skeleton.lifecycle
            length = skeleton.length
        }

        /**
         * Convenience function to set [view] as the [Target].
         */
        fun target(view: TextView) = apply {
            target(TextViewTarget(view))
        }

        /**
         * Convenience function to create and set the [Target].
         */
        inline fun target(
                crossinline onStart: () -> Unit = {},
                crossinline onError: () -> Unit = {},
                crossinline onSuccess: (skeleton: KoletonView) -> Unit = {}
        ) = target(object : Target {
            override fun onStart() = onStart()
            override fun onError() = onError()
            override fun onSuccess(skeleton: KoletonView) = onSuccess(skeleton)
        })

        fun target(target: Target?) = apply {
            this.target = target
        }

        fun lifecycle(lifecycle: Lifecycle?) = apply {
            this.lifecycle = lifecycle
        }

        /**
         * Create a new [ViewSkeleton] instance.
         */
        fun build(): TextViewSkeleton {
            return TextViewSkeleton(
                    context,
                    target,
                    lifecycle,
                    colorResId,
                    cornerRadius,
                    isShimmerEnabled,
                    shimmer,
                    lineSpacing,
                    length
            )
        }
    }
}