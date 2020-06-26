package koleton

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleObserver
import com.facebook.shimmer.ShimmerFrameLayout
import koleton.annotation.ExperimentalKoletonApi
import koleton.custom.*
import koleton.memory.DelegateService
import koleton.memory.SkeletonService
import koleton.skeleton.RecyclerViewSkeleton
import koleton.skeleton.Skeleton
import koleton.skeleton.ViewSkeleton
import koleton.target.RecyclerViewTarget
import koleton.target.SimpleViewTarget
import koleton.target.ViewTarget
import koleton.util.*
import kotlinx.coroutines.*

@OptIn(ExperimentalKoletonApi::class)
internal class MainSkeletonLoader(
    private val context: Context,
    override val defaults: DefaultSkeletonOptions
) : SkeletonLoader {

    companion object {
        private const val TAG = "MainSkeletonLoader"
    }

    private val loaderScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, throwable.message.orEmpty())
    }

    private val delegateService = DelegateService(this)

    override fun load(skeleton: Skeleton) {
        val job = loaderScope.launch(exceptionHandler) { loadInternal(skeleton) }
        val target = skeleton.target as? ViewTarget<*>
        target?.view?.koletonManager?.setCurrentSkeletonJob(job)
    }

    override fun generate(skeleton: Skeleton): KoletonView {
        return generateKoletonView(skeleton)
    }

    private suspend fun loadInternal(skeleton: Skeleton) =
        withContext(Dispatchers.Main.immediate) {
            val (lifecycle, mainDispatcher) = SkeletonService().lifecycleInfo(skeleton)
            val targetDelegate = delegateService.createTargetDelegate(skeleton)
            val deferred = async(mainDispatcher, CoroutineStart.LAZY) {
                val target = skeleton.target
                if (target is ViewTarget<*> && target is LifecycleObserver) {
                    lifecycle.addObserver(target)
                    with(target.view) {
                        if (measuredWidth > 0 && measuredHeight > 0) {
                            val koletonView = generateKoletonView(skeleton)
                            koletonManager.setCurrentKoletonView(koletonView)
                            targetDelegate.success(koletonView)
                        }
                    }
                }
            }
            val skeletonDelegate = delegateService.createSkeletonDelegate(
                skeleton,
                targetDelegate,
                lifecycle,
                mainDispatcher,
                deferred
            )
            deferred.invokeOnCompletion { throwable ->
                loaderScope.launch(Dispatchers.Main.immediate) { skeletonDelegate?.onComplete() }
            }
            deferred.await()
        }

    override fun hide(view: View, koletonView: KoletonView) {
        koletonView.hideSkeleton()
        val skeletonView = koletonView as ShimmerFrameLayout
        val originalParent = skeletonView.getParentViewGroup()
        skeletonView.removeView(view)
        originalParent.removeView(skeletonView)
        originalParent.addView(view)
    }

    private fun generateKoletonView(skeleton: Skeleton): KoletonView {
        return when (skeleton) {
            is RecyclerViewSkeleton -> generateRecyclerView(skeleton)
            is ViewSkeleton -> generateSimpleView(skeleton)
        }
    }

    private fun generateRecyclerView(skeleton: RecyclerViewSkeleton) = with(skeleton) {
        return@with if (target is RecyclerViewTarget) {
            val attributes = RecyclerViewAttributes(
                view = target.view,
                color = context.getColorCompat(colorResId ?: defaults.colorResId),
                cornerRadius = cornerRadius ?: defaults.cornerRadius.px,
                isShimmerEnabled = isShimmerEnabled ?: defaults.isShimmerEnabled,
                shimmer = shimmer ?: defaults.shimmer,
                lineSpacing = defaults.lineSpacing,
                itemLayout = itemLayoutResId,
                itemCount = itemCount ?: defaults.itemCount
            )
            target.view.generateRecyclerKoletonView(attributes)
        } else {
            RecyclerKoletonView(context)
        }
    }

    private fun generateSimpleView(skeleton: ViewSkeleton) = with(skeleton) {
        return@with if (target is SimpleViewTarget) {
            val attributes = SimpleViewAttributes(
                color = context.getColorCompat(colorResId ?: defaults.colorResId),
                cornerRadius = cornerRadius ?: defaults.cornerRadius.px,
                isShimmerEnabled = isShimmerEnabled ?: defaults.isShimmerEnabled,
                shimmer = shimmer ?: defaults.shimmer,
                lineSpacing = defaults.lineSpacing
            )
            target.view.generateSimpleKoletonView(attributes)
        } else {
            SimpleKoletonView(context)
        }
    }
}