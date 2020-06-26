package koleton.sample.list.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import koleton.sample.utils.State
import koleton.sample.model.Journey
import koleton.sample.utils.Helper
import kotlinx.coroutines.*
import java.util.concurrent.Executor

class JourneyDataSource(
    private val retryExecutor: Executor,
    private val delayMillis: Long
) : PageKeyedDataSource<String, Journey>() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private var retry: (() -> Any)? = null

    val state = MutableLiveData<Int>()

    val initialLoad = MutableLiveData<Int>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, Journey>
    ) {
        // Ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Journey>) {
        scope.launch {
            state.postValue(State.LOADING)
            delay(delayMillis)
            val items = Helper.generateDataSet()
            callback.onResult(items, items.last().id)
            state.postValue(State.LOADED)
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Journey>
    ) {
        scope.launch {
            delay(delayMillis)
            val items = Helper.generateDataSet()
            callback.onResult(items, items.first().id, items.last().id)
            state.postValue(State.INITIAL_LOADED)
        }
    }

    override fun invalidate() {
        job.cancel()
        super.invalidate()
    }
}