package koleton.custom

import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer

sealed class Attributes {
    abstract val color: Int
    abstract val cornerRadius: Int
    abstract val isShimmerEnabled: Boolean
    abstract val shimmer: Shimmer
    abstract val lineSpacing: Int
}

data class RecyclerViewAttributes(
    val view: RecyclerView,
    @ColorInt override val color: Int,
    @Px override val cornerRadius: Int,
    override val isShimmerEnabled: Boolean,
    override val shimmer: Shimmer,
    override val lineSpacing: Int,
    @LayoutRes val itemLayout: Int,
    val itemCount: Int
): Attributes()

data class SimpleViewAttributes(
    @ColorInt override val color: Int,
    @Px override val cornerRadius: Int,
    override val isShimmerEnabled: Boolean,
    override val shimmer: Shimmer,
    override val lineSpacing: Int
): Attributes()