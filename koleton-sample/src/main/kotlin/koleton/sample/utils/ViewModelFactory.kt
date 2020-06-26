package koleton.sample.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import koleton.sample.list.JourneyViewModel
import koleton.sample.list.repository.JourneyRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: JourneyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(JourneyViewModel::class.java) -> JourneyViewModel(repository)
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}