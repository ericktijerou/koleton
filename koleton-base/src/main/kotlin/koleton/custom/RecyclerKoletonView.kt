package koleton.custom

import android.content.Context
import android.util.AttributeSet

internal class RecyclerKoletonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : KoletonView(context, attrs, defStyleAttr) {

    override var isSkeletonShown: Boolean = false

    var attributes: RecyclerViewAttributes? = null
        set(value) {
            field = value
            value?.let { applyAttributes() }
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

    override fun applyAttributes() {
        attributes?.run {
            adapter = view.adapter
            if (!isShimmerEnabled) hideShimmer() else setShimmer(shimmer)
            skeletonAdapter = KoletonAdapter(itemLayout, itemCount, this)
            if (isSkeletonShown) {
                showSkeleton()
            }
        }
    }
}