package koleton

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import koleton.annotation.ExperimentalKoletonApi
import koleton.custom.*
import koleton.memory.DelegateService
import koleton.memory.SkeletonService
import koleton.skeleton.RecyclerViewSkeleton
import koleton.skeleton.Skeleton
import koleton.skeleton.ViewSkeleton
import koleton.target.RecyclerViewTarget
import koleton.target.SimpleViewTarget
import koleton.target.Target
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
        Log.e("Error", throwable.message.orEmpty())
    }

    private val delegateService = DelegateService(this)

    override fun execute(skeleton: Skeleton) {
        val job = loaderScope.launch(exceptionHandler) { executeInternal(skeleton) }
        val target = skeleton.target as? ViewTarget<*>
        target?.view?.koletonManager?.setCurrentSkeletonJob(job)
    }

    private suspend fun executeInternal(skeleton: Skeleton) =
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

    override fun hide(target: Target?, koletonView: KoletonView) {
        koletonView.hideSkeleton()
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
                itemLayout = itemLayoutResId,
                itemCount = itemCount ?: defaults.itemCount
            )
            target.view.generateKoletonRecyclerView(attributes)
        } else {
            KoletonRecyclerView(context)
        }
    }

    private fun generateSimpleView(skeleton: ViewSkeleton) = with(skeleton) {
        return@with if (target is SimpleViewTarget) {
            val attributes = SimpleViewAttributes(
                color = context.getColorCompat(colorResId ?: defaults.colorResId),
                cornerRadius = cornerRadius ?: defaults.cornerRadius.px,
                isShimmerEnabled = isShimmerEnabled ?: defaults.isShimmerEnabled
            )
            target.view.generateKoletonFrameLayout(attributes)
        } else {
            KoletonFrameLayout(context)
        }
    }
}