package koleton.sample.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import koleton.sample.R
import koleton.sample.databinding.FragmentJourneyListBinding
import koleton.sample.model.Journey
import koleton.sample.utils.*

class JourneyListFragment : Fragment() {

    private val journeyListAdapter = JourneyListAdapter { journey -> onJourneyClick(journey) }

    private val viewModel: JourneyViewModel by viewModels { getViewModelFactory() }

    lateinit var binding: FragmentJourneyListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJourneyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.ivRefresh.setOnClickListener { onRefreshClickListener() }
        getJourneyList()
    }

    private fun setupRecyclerView() {
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = journeyListAdapter
        }
        viewModel.state.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.posts.observe(viewLifecycleOwner, journeyStateObserver)
    }

    private val viewStateObserver = Observer<Int> { state ->
        when (state) {
            State.INITIAL_LOADED -> onLoaded()
            else -> journeyListAdapter.setState(state)
        }
    }

    private val journeyStateObserver = Observer<PagedList<Journey>> { list ->
        journeyListAdapter.submitList(list) {
            val layoutManager = (binding.rvUsers.layoutManager as LinearLayoutManager)
            val position = layoutManager.findFirstCompletelyVisibleItemPosition()
            if (position != RecyclerView.NO_POSITION) {
                binding.rvUsers.scrollToPosition(position)
            }
        }
    }

    private fun onJourneyClick(journey: Journey) {
        navigateToJourneyDetail(journey)
    }

    private fun getJourneyList() {
        onLoadInitial()
        viewModel.load(DEFAULT_DELAY)
    }

    private fun onLoadInitial() {
        binding.apply {
            ivRefresh.gone()
            rvUsers.loadSkeleton(R.layout.item_journey)
            tvSubtitle.loadSkeleton(length = 20)
        }
    }

    private fun onLoaded() {
        binding.apply {
            ivRefresh.visible()
            rvUsers.hideSkeleton()
            tvSubtitle.hideSkeleton()
            tvSubtitle.text = requireContext().getString(R.string.label_see_your_journey)
        }
    }

    private fun onRefreshClickListener() {
        onLoadInitial()
        viewModel.refresh()
    }
}