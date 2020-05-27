package koleton.custom

import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

sealed class Attributes {
    abstract val colorResId: Int
    abstract val cornerRadius: Int
    abstract val isShimmerEnabled: Boolean
}

data class RecyclerViewAttributes(
    val view: RecyclerView,
    @ColorRes override val colorResId: Int,
    @Px override val cornerRadius: Int,
    override val isShimmerEnabled: Boolean,
    @LayoutRes val itemLayout: Int,
    val itemCount: Int
): Attributes()

data class SimpleViewAttributes(
    @ColorRes override val colorResId: Int,
    @Px override val cornerRadius: Int,
    override val isShimmerEnabled: Boolean
): Attributes()