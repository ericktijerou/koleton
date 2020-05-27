package koleton.memory

import android.view.ViewTreeObserver
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import koleton.custom.KoletonView
import koleton.util.isMainThread
import koleton.util.notNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*


internal class ViewTargetSkeletonManager: ViewTreeObserver.OnGlobalLayoutListener {

    private var currentSkeleton: ViewTargetSkeletonDelegate? = null

    private var currentKoletonView: KoletonView? = null

    @Volatile
    private var pendingClear: Job? = null

    @Volatile
    var currentSkeletonId: UUID? = null
        private set

    @Volatile
    var currentSkeletonJob: Job? = null
        private set

    private var isRestart = false
    private var skipAttach = true

    @MainThread
    fun setCurrentKoletonView(koletonView: KoletonView?) {
        currentKoletonView = koletonView
    }

    @MainThread
    fun setCurrentSkeleton(skeleton: ViewTargetSkeletonDelegate?) {
        if (isRestart) {
            isRestart = false
        } else {
            pendingClear?.cancel()
            pendingClear = null
        }

        currentSkeleton?.dispose()
        currentSkeleton = skeleton
        skipAttach = true
    }

    @MainThread
    fun hideSkeleton() {
        currentSkeletonId = null
        currentSkeletonJob = null
        pendingClear?.cancel()
        pendingClear = CoroutineScope(Dispatchers.Main.immediate).launch {
            currentSkeleton.notNull { currentKoletonView.notNull { view -> it.hideSkeleton(view) } }
            setCurrentSkeleton(null)
            setCurrentKoletonView(null)
        }
    }

    /** Set the current [job] attached to this view and assign it an ID. */
    @AnyThread
    fun setCurrentSkeletonJob(job: Job): UUID {
        val skeletonId = newSkeletonId()
        currentSkeletonId = skeletonId
        currentSkeletonJob = job
        return skeletonId
    }

    @AnyThread
    private fun newSkeletonId(): UUID {
        val skeletonId = currentSkeletonId
        if (skeletonId != null && isRestart && isMainThread()) {
            return skeletonId
        }
        return UUID.randomUUID()
    }

    override fun onGlobalLayout() {
        currentSkeleton.notNull { skeleton ->
            skeleton.getViewTarget().notNull {
                if (it.measuredWidth > 0 && it.measuredHeight > 0) {
                    it.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    isRestart = true
                    skeleton.restart()
                }
            }
        }
    }
}
