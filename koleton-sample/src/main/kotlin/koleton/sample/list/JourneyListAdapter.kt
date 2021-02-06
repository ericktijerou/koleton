package koleton.sample.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import koleton.api.generateSkeleton
import koleton.custom.KoletonView
import koleton.sample.databinding.ItemJourneyBinding
import koleton.sample.model.Journey
import koleton.sample.utils.State
import koleton.sample.utils.visible

class JourneyListAdapter(private val clickListener: (Journey) -> Unit) :
    PagedListAdapter<Journey, RecyclerView.ViewHolder>(POST_COMPARATOR) {

    private var state = State.NONE

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_VIEW -> (holder as JourneyViewHolder).bind(getItem(position), clickListener)
            TYPE_SKELETON -> (holder as SkeletonViewHolder).koletonView.showSkeleton()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        val itemBinding =
            ItemJourneyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when (viewType) {
            TYPE_VIEW -> JourneyViewHolder(itemBinding)
            TYPE_SKELETON -> SkeletonViewHolder(itemBinding.root.generateSkeleton())
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_SKELETON
        } else {
            TYPE_VIEW
        }
    }

    private fun hasExtraRow() = state != State.NONE && state != State.LOADED

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setState(@State newState: Int) {
        val previousState = this.state
        val hadExtraRow = hasExtraRow()
        this.state = newState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    class JourneyViewHolder(private val itemBinding: ItemJourneyBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(journey: Journey?, clickListener: (Journey) -> Unit) = with(itemView) {
            journey?.run {
                itemBinding.apply {
                    val dateTime = "$date, $pickUpTime"
                    tvDate.text = dateTime
                    tvAddress.text = dropOffPoint
                    tvPrice.text = total
                    ivCarType.setImageResource(carIcon)
                    tvDetails.visible()
                    itemView.setOnClickListener { clickListener(journey) }
                }
            }
        }
    }

    class SkeletonViewHolder(val koletonView: KoletonView) : RecyclerView.ViewHolder(koletonView)

    companion object {
        private const val TYPE_VIEW = 1
        private const val TYPE_SKELETON = 2

        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Journey>() {
            override fun areContentsTheSame(oldItem: Journey, newItem: Journey): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Journey, newItem: Journey): Boolean =
                oldItem.id == newItem.id
        }
    }
}