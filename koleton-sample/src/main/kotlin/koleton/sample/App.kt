package koleton.sample

import android.app.Application
import com.facebook.shimmer.Shimmer
import koleton.SkeletonLoader
import koleton.SkeletonLoaderFactory
import koleton.sample.utils.ITEM_COUNT
import koleton.sample.utils.getDimension

class App : Application(), SkeletonLoaderFactory {
    override fun newSkeletonLoader(): SkeletonLoader {
        return SkeletonLoader.Builder(this)
            .color(R.color.colorSkeleton)
            .cornerRadius(getDimension(R.dimen.default_corner_radius))
            .lineSpacing(getDimension(R.dimen.default_line_spacing))
            .itemCount(ITEM_COUNT)
            .shimmer(true)
            .shimmer(getCustomShimmer())
            .build()
    }

    private fun getCustomShimmer(): Shimmer {
        return Shimmer.AlphaHighlightBuilder()
            .setDuration(1000)
            .setBaseAlpha(0.5f)
            .setHighlightAlpha(0.9f)
            .setWidthRatio(1f)
            .setHeightRatio(1f)
            .setDropoff(1f)
            .build()
    }
}