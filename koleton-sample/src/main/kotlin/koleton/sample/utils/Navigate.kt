package koleton.sample.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import koleton.sample.list.JourneyListFragmentDirections
import koleton.sample.model.Journey

fun Fragment.navigateToJourneyDetail(journey: Journey) {
    val action = JourneyListFragmentDirections.actionJourneyListToDetail(journey)
    findNavController().navigate(action)
}

