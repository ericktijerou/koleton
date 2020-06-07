package koleton.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import koleton.util.visible
import kotlinx.android.synthetic.main.item_journey.view.*

class JourneyListAdapter(private val journeyList: List<Journey>, private val clickListener: (Journey) -> Unit) :
    RecyclerView.Adapter<JourneyListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_journey, parent, false)
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(journeyList[position], clickListener)
    }

    override fun getItemCount() = journeyList.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvDate = itemView.tvDate
        private val tvAddress = itemView.tvAddress
        private val tvPrice = itemView.tvPrice
        private val ivCarType = itemView.ivCarType
        private val tvDetails = itemView.tvDetails

        fun bind(journey: Journey, clickListener: (Journey) -> Unit) = with(journey) {
            tvDate?.text = date
            tvAddress?.text = address
            tvPrice?.text = price
            ivCarType?.setImageResource(icon)
            tvDetails.visible()
            itemView.setOnClickListener { clickListener(journey) }
        }
    }
}