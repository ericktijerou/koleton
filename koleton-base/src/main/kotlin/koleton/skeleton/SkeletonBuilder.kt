package koleton.skeleton

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.Px
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import koleton.annotation.BuilderMarker
import koleton.custom.KoletonView
import koleton.target.RecyclerViewTarget
import koleton.target.SimpleViewTarget
import koleton.target.Target
import koleton.util.self


/** Base class for [ViewSkeletonBuilder] */
@BuilderMarker
sealed class SkeletonBuilder<T : SkeletonBuilder<T>> {

    @JvmField protected val context: Context
    @JvmField @Px protected var cornerRadius: Int?
    @JvmField @ColorRes protected var colorResId: Int?
    @JvmField protected var isShimmerEnabled: Boolean?

    constructor(context: Context) {
        this.context = context
        this.colorResId = null
        this.cornerRadius = null
        this.isShimmerEnabled = null
    }

    constructor(skeleton: Skeleton, context: Context) {
        this.context = context
        this.colorResId = skeleton.colorResId
        this.cornerRadius = skeleton.cornerRadius
        this.isShimmerEnabled = skeleton.isShimmerEnabled
    }

    /**
     * Set the skeleton corner radius.
     */
    fun cornerRadius(@Px radius: Int): T = self {
        this.cornerRadius = radius
    }

    /**
     * Set the skeleton color.
     */
    fun color(@ColorRes color: Int): T = self {
        this.colorResId = color
    }

    /**
     * Set the skeleton shimmer.
     */
    fun shimmer(enable: Boolean): T = self {
        this.isShimmerEnabled = enable
    }
}

/** Builder for a [ViewSkeleton]. */
class ViewSkeletonBuilder : SkeletonBuilder<ViewSkeletonBuilder> {

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
            isShimmerEnabled
        )
    }
}

/** Builder for a [RecyclerViewSkeletonBuilder]. */
class RecyclerViewSkeletonBuilder : SkeletonBuilder<ViewSkeletonBuilder> {

    private var target: Target?
    private var lifecycle: Lifecycle?
    @LayoutRes private var itemLayoutResId: Int
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
            itemCount
        )
    }
}