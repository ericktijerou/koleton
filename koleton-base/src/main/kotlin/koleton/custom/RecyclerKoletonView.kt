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

    private var originalLayoutManager = attributes?.view?.layoutManager

    private var skeletonAdapter: KoletonAdapter? = null

    override fun hideSkeleton() {
        isSkeletonShown = false
        hideShimmer()
        attributes?.run {
            view.adapter = adapter
            view.layoutManager = originalLayoutManager
        }
    }

    override fun showSkeleton() {
        isSkeletonShown = true
        attributes?.run {
            view.adapter = skeletonAdapter
            view.layoutManager = layoutManager
        }
    }

    override fun applyAttributes() {
        attributes?.run {
            adapter = view.adapter
            skeletonAdapter = KoletonAdapter(itemLayout, itemCount, this)
            originalLayoutManager = view.layoutManager

            if (!isShimmerEnabled) hideShimmer() else setShimmer(shimmer)
            if (isSkeletonShown) {
                showSkeleton()
            }
        }
    }
}