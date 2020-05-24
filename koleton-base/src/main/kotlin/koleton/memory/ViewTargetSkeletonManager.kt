package koleton.memory

import android.view.View
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import koleton.util.notNull


internal class ViewTargetSkeletonManager : View.OnAttachStateChangeListener {

    private var currentSkeleton: ViewTargetSkeletonDelegate? = null

    @Volatile var skeletonId: Int? = null
        private set

    private var isRestart = false
    private var skipAttach = true

    @MainThread
    fun setCurrentSkeleton(skeleton: ViewTargetSkeletonDelegate?) {
        if (isRestart) {
            isRestart = false
        }
        currentSkeleton = skeleton
        skipAttach = true
    }

    @MainThread
    fun hideSkeleton() {
        currentSkeleton.notNull { skeleton -> skeletonId?.let { skeleton.hideSkeleton(it) } }
    }

    @AnyThread
    fun setSkeletonId(skeletonId: Int) {
        this.skeletonId = skeletonId
    }

    @MainThread
    override fun onViewAttachedToWindow(v: View) {
        if (skipAttach) {
            skipAttach = false
            return
        }

        currentSkeleton?.let {
            isRestart = true
        }
    }

    @MainThread
    override fun onViewDetachedFromWindow(v: View) {
        skipAttach = false
    }
}
