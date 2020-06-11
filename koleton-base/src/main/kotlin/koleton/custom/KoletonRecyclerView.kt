package koleton.custom

import android.content.Context
import android.util.AttributeSet
import com.facebook.shimmer.ShimmerFrameLayout

internal class KoletonRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ShimmerFrameLayout(context, attrs, defStyleAttr), KoletonView {

    private var isSkeletonShown: Boolean = false

    var attributes: RecyclerViewAttributes? = null
        set(value) {
            field = value
            value?.let { applyAttributes(it) }
        }

    private var adapter = attributes?.view?.adapter

    private var skeletonAdapter: KoletonAdapter? = null

    override fun hideSkeleton() {
        isSkeletonShown = false
        hideShimmer()
        attributes?.view?.adapter = adapter
    }

    override fun showSkeleton() {
        isSkeletonShown = true
        attributes?.view?.adapter = skeletonAdapter
    }

    private fun applyAttributes(attributes: RecyclerViewAttributes) = with(attributes) {
        adapter = view.adapter
        if (!attributes.isShimmerEnabled) hideShimmer() else setShimmer(shimmer)
        skeletonAdapter = KoletonAdapter(itemLayout, itemCount, attributes)
        if (isSkeletonShown) {
            showSkeleton()
        }
    }
}