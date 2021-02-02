package koleton.custom

import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer

sealed class Attributes {
    abstract val color: Int
    abstract val cornerRadius: Float
    abstract val isShimmerEnabled: Boolean
    abstract val shimmer: Shimmer
    abstract val lineSpacing: Float
}

data class RecyclerViewAttributes(
    val view: RecyclerView,
    @ColorInt override val color: Int,
    @Px override val cornerRadius: Float,
    override val isShimmerEnabled: Boolean,
    override val shimmer: Shimmer,
    override val lineSpacing: Float,
    @LayoutRes val itemLayout: Int,
    val itemCount: Int
): Attributes()

data class SimpleViewAttributes(
    @ColorInt override val color: Int,
    @Px override val cornerRadius: Float,
    override val isShimmerEnabled: Boolean,
    override val shimmer: Shimmer,
    override val lineSpacing: Float
): Attributes()

data class TextViewAttributes(
        val view: TextView,
        @ColorInt override val color: Int,
        @Px override val cornerRadius: Float,
        override val isShimmerEnabled: Boolean,
        override val shimmer: Shimmer,
        override val lineSpacing: Float,
        val length: Int
): Attributes()
