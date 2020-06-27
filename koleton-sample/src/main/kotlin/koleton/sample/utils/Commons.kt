package koleton.sample.utils

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.annotation.DimenRes
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

fun Context.getDimension(@DimenRes resId: Int): Float {
    return resources.getDimension(resId)
}

fun getViewModelFactory(): ViewModelFactory {
    val executor = Executors.newFixedThreadPool(5)
    return ViewModelFactory(JourneyRepository(executor))
}

const val DEFAULT_DELAY: Long = 3000
const val DEFAULT_PAGE_SIZE: Int = 6
const val ITEM_COUNT: Int = 3