package koleton.util

import android.text.Spannable
import android.text.SpannableString
import androidx.annotation.ColorInt

fun spannable(func: () -> SpannableString) = func()

private fun span(s: CharSequence, o: Any) = (if (s is String) SpannableString(s) else s as? SpannableString
    ?: SpannableString(EMPTY_STRING)).apply { setSpan(o, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }

fun background(@ColorInt color: Int, radius: Float, lineSpacing: Int, s: CharSequence) = span(s, RoundedBackgroundColorSpan(color, radius, lineSpacing))