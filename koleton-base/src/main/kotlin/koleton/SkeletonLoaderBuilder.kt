package koleton

import android.content.Context
import koleton.annotation.BuilderMarker

@BuilderMarker
class SkeletonLoaderBuilder(context: Context) {

    private val applicationContext = context.applicationContext
    private var defaults = DefaultSkeletonOptions()

    /**
     * Create a new [SkeletonLoader] instance.
     */
    fun build(): SkeletonLoader {
        return MainSkeletonLoader(
            context = applicationContext,
            defaults = defaults
        )
    }
}