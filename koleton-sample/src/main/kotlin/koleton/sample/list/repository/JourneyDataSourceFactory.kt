package koleton.sample.list.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import koleton.sample.model.Journey
import java.util.concurrent.Executor

class JourneyDataSourceFactory(
    private val retryExecutor: Executor,
    private val delay: Long
) : DataSource.Factory<String, Journey>() {
    val sourceLiveData = MutableLiveData<JourneyDataSource>()
    override fun create(): DataSource<String, Journey> {
        val source = JourneyDataSource(retryExecutor, delay)
        sourceLiveData.postValue(source)
        return source
    }
}
