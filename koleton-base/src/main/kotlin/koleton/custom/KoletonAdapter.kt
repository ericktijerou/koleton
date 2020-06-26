package koleton.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import koleton.util.generateSimpleKoletonView

internal class KoletonAdapter(
    @LayoutRes private val layoutResId: Int,
    private val itemCount: Int,
    private val attributes: Attributes
) : RecyclerView.Adapter<KoletonAdapter.KoletonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KoletonViewHolder {
        val originView = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        val skeleton = originView.generateSimpleKoletonView(attributes)
        return KoletonViewHolder(skeleton.also { it.showSkeleton() })
    }

    override fun onBindViewHolder(holder: KoletonViewHolder, position: Int) = Unit

    override fun getItemCount() = itemCount

    internal class KoletonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
