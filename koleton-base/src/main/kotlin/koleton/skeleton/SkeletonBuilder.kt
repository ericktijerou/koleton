package koleton.skeleton

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import koleton.annotation.BuilderMarker
import koleton.target.Target
import koleton.target.ViewTarget

/** Base class for [ViewSkeletonBuilder] and [TextViewSkeletonBuilder]. */
@BuilderMarker
sealed class SkeletonBuilder<T : SkeletonBuilder<T>> {

    @JvmField protected val context: Context
    @JvmField @DimenRes protected var borderRadius: Int
    @JvmField @ColorRes protected var colorResId: Int
    @JvmField @ColorRes protected var shimmerColorResId: Int
    @JvmField protected var isShimmerEnabled: Boolean
    @JvmField protected var shimmerDuration: Int
    @JvmField protected var shimmerDirection: Int
    @JvmField protected var shimmerTilt: Int

    constructor(context: Context) {
        this.context = context
        this.colorResId = 0
        this.borderRadius = 0
        this.isShimmerEnabled = true
        this.shimmerColorResId = 0
        this.shimmerDuration = 0
        this.shimmerDirection = 0
        this.shimmerTilt = 0
    }

    constructor(skeleton: Skeleton, context: Context) {
        this.context = context
        this.colorResId = skeleton.colorResId
        this.borderRadius = skeleton.borderRadiusId
        this.isShimmerEnabled = skeleton.isShimmerEnabled
        this.shimmerColorResId = skeleton.shimmerColorResId
        this.shimmerDuration = skeleton.shimmerDuration
        this.shimmerDirection = skeleton.shimmerDirection
        this.shimmerTilt = skeleton.shimmerTilt
    }
}

/** Builder for a [ViewSkeleton]. */
class ViewSkeletonBuilder : SkeletonBuilder<ViewSkeletonBuilder> {

    private var target: Target?
    @DrawableRes private var backgroundResId: Int
    private var backgroundDrawable: Drawable?

    constructor(context: Context) : super(context) {
        target = null
        backgroundResId = 0
        backgroundDrawable = null
    }

    @JvmOverloads
    constructor(
        skeleton: ViewSkeleton,
        context: Context = skeleton.context
    ) : super(skeleton, context) {
        target = skeleton.target
        backgroundResId = skeleton.backgroundResId
        backgroundDrawable = skeleton.backgroundDrawable
    }

    /**
     * Convenience function to set [view] as the [Target].
     */
    fun target(view: View) = apply {
        target(ViewTarget(view))
    }

    /**
     * Convenience function to create and set the [Target].
     */
    inline fun target(
        crossinline onStart: () -> Unit = {},
        crossinline onError: () -> Unit = {},
        crossinline onSuccess: (skeleton: View) -> Unit = {}
    ) = target(object : Target {
        override fun onStart() = onStart()
        override fun onError() = onError()
        override fun onSuccess(skeleton: View) = onSuccess(skeleton)
    })

    fun target(target: Target?) = apply {
        this.target = target
    }

    /**
     * Create a new [ViewSkeleton] instance.
     */
    fun build(): ViewSkeleton {
        return ViewSkeleton(
            context,
            target,
            colorResId,
            borderRadius,
            isShimmerEnabled,
            shimmerColorResId,
            shimmerDuration,
            shimmerDirection,
            shimmerTilt,
            backgroundResId,
            backgroundDrawable
        )
    }
}

/** Builder for a [TextViewSkeleton]. */
class TextViewSkeletonBuilder : SkeletonBuilder<TextViewSkeletonBuilder> {

    private var target: Target?
    private var isLineSkeletonEnabled: Boolean

    constructor(context: Context) : super(context) {
        target = null
        isLineSkeletonEnabled = true
    }

    @JvmOverloads
    constructor(
        skeleton: TextViewSkeleton,
        context: Context = skeleton.context
    ) : super(skeleton, context) {
        target = skeleton.target
        isLineSkeletonEnabled = skeleton.isLineSkeletonEnabled
    }

    /**
     * Convenience function to set [textView] as the [Target].
     */
    fun target(textView: TextView) = apply {
        target(ViewTarget(textView))
    }

    /**
     * Convenience function to create and set the [Target].
     */
    inline fun target(
        crossinline onStart: () -> Unit = {},
        crossinline onError: () -> Unit = {},
        crossinline onSuccess: (skeleton: View) -> Unit = {}
    ) = target(object : Target {
        override fun onStart() = onStart()
        override fun onError() = onError()
        override fun onSuccess(skeleton: View) = onSuccess(skeleton)
    })

    fun target(target: Target?) = apply {
        this.target = target
    }

    /**
     * Create a new [TextViewSkeleton] instance.
     */
    fun build(): TextViewSkeleton {
        return TextViewSkeleton(
            context,
            target,
            colorResId,
            borderRadius,
            isShimmerEnabled,
            shimmerColorResId,
            shimmerDuration,
            shimmerDirection,
            shimmerTilt,
            isLineSkeletonEnabled
        )
    }
}