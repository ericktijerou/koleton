package koleton.util

import android.text.Spannable
import android.text.SpannableString
import com.ericktijerou.mockplaceholder.RoundedBackgroundColorSpan

fun spannable(func: () -> SpannableString) = func()
private fun span(s: CharSequence, o: Any) = (if (s is String) SpannableString(s) else s as? SpannableString
    ?: SpannableString(EMPTY_STRING)).apply { setSpan(o, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }

fun background(color: Int, radius: Float, s: CharSequence) = span(s, RoundedBackgroundColorSpan(color, radius))