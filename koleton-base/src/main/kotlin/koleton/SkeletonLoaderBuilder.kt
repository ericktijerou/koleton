package koleton

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import koleton.annotation.BuilderMarker

@BuilderMarker
class SkeletonLoaderBuilder(context: Context) {

    private val applicationContext = context.applicationContext
    private var defaults = DefaultSkeletonOptions()

    /**
     * Set the default placeholder drawable to use when a skeleton starts.
     */
    fun background(@DrawableRes drawableResId: Int) = apply {
        //this.defaults = this.defaults.copy(placeholder = applicationContext.getDrawableCompat(drawableResId))
    }


    /**
     * Create a new [SkeletonLoader] instance.
     */
    fun build(): SkeletonLoader {
        return ViewSkeletonLoader(
            context = applicationContext,
            defaults = defaults
        )
    }
}