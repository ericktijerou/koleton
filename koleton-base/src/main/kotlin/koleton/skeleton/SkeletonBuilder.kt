package koleton.skeleton

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.Px
import com.facebook.shimmer.Shimmer
import koleton.annotation.BuilderMarker
import koleton.util.self

/** Base class for [ViewSkeleton.Builder] and [RecyclerViewSkeleton.Builder] */
@BuilderMarker
open class SkeletonBuilder<T : SkeletonBuilder<T>> {

    @JvmField protected val context: Context
    @JvmField @Px protected var cornerRadius: Float?
    @JvmField @ColorRes protected var colorResId: Int?
    @JvmField protected var isShimmerEnabled: Boolean?
    @JvmField protected var shimmer: Shimmer?
    @JvmField @Px protected var lineSpacing: Float?

    constructor(context: Context) {
        this.context = context
        this.colorResId = null
        this.cornerRadius = null
        this.isShimmerEnabled = null
        this.shimmer = null
        this.lineSpacing = null
    }

    constructor(skeleton: Skeleton, context: Context) {
        this.context = context
        this.colorResId = skeleton.colorResId
        this.cornerRadius = skeleton.cornerRadius
        this.isShimmerEnabled = skeleton.isShimmerEnabled
        this.shimmer = skeleton.shimmer
        this.lineSpacing = skeleton.lineSpacing
    }

    /**
     * Set the radius in pixels of the corners of the skeleton.
     */
    fun cornerRadius(@Px radius: Float): T = self {
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

    /**
     * Set the skeleton shimmer.
     */
    fun shimmer(shimmer: Shimmer): T = self {
        this.shimmer = shimmer
    }

    /**
     * Set the space between each line of the skeleton associated with a TextView.
     */
    fun lineSpacing(@Px lineSpacing: Float): T = self {
        this.lineSpacing = lineSpacing
    }
}