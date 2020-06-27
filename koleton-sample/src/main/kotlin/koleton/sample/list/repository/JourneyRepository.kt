package koleton.sample.list.repository

import androidx.annotation.MainThread
import androidx.lifecycle.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import koleton.sample.model.Journey
import koleton.sample.utils.DEFAULT_PAGE_SIZE
import koleton.sample.utils.Helper
import koleton.sample.utils.Listing
import java.util.concurrent.Executor

class JourneyRepository(
    private val retryExecutor: Executor
) {
    @MainThread
    fun getJourneyList(delay: Long): Listing<Journey> {
        val sourceFactory = JourneyDataSourceFactory(retryExecutor, delay)
        val config = PagedList.Config.Builder()
            .setPageSize(DEFAULT_PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        val livePagedList = LivePagedListBuilder(sourceFactory, config)
            .setFetchExecutor(retryExecutor)
            .build()
        val refreshState = sourceFactory.sourceLiveData.switchMap {
            it.initialLoad
        }
        return Listing(
            pagedList = livePagedList,
            networkState = sourceFactory.sourceLiveData.switchMap {
                it.state
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )
    }
}

