package koleton.sample.utils

import android.view.View
import koleton.sample.list.repository.JourneyRepository
import java.util.concurrent.Executors

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun getViewModelFactory(): ViewModelFactory {
    val executor = Executors.newFixedThreadPool(5)
    return ViewModelFactory(JourneyRepository(executor))
}

const val DEFAULT_DELAY: Long = 3000