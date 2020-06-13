package koleton.sample.list

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import koleton.sample.model.Journey
import koleton.sample.R
import koleton.sample.utils.*
import kotlinx.android.synthetic.main.fragment_journey_list.*

class JourneyListFragment: Fragment() {

    private val journeyListAdapter = JourneyListAdapter { journey -> onJourneyClick(journey) }
    private val handler by lazy { Handler() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_journey_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        ivRefresh?.setOnClickListener { getJourneyList() }
        getJourneyList()
    }

    private fun setupRecyclerView() {
        rvUsers?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = journeyListAdapter
        }
    }

    private fun onSuccess(list: List<Journey>) {
        ivRefresh?.visible()
        rvUsers?.hideSkeleton()
        journeyListAdapter.swap(list)
    }

    private fun onJourneyClick(journey: Journey) {
        navigateToJourneyDetail(journey)
    }

    private fun getJourneyList() {
        ivRefresh?.gone()
        rvUsers?.loadSkeleton(R.layout.item_journey) { color(R.color.colorSkeleton) }
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({ onSuccess(DataSource.generateDataSet()) }, DELAY)
    }

    companion object {
        const val DELAY: Long = 3000
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}