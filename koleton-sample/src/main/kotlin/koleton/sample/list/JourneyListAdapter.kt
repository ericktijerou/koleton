package koleton.sample.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import koleton.sample.R
import koleton.sample.model.Journey
import koleton.sample.utils.visible
import kotlinx.android.synthetic.main.item_journey.view.*

class JourneyListAdapter(private val clickListener: (Journey) -> Unit) :
    RecyclerView.Adapter<JourneyListAdapter.MyViewHolder>() {

    private val journeyList = arrayListOf<Journey>()

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

    fun swap(list: List<Journey>) {
        journeyList.clear()
        journeyList.addAll(list)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvDate = itemView.tvDate
        private val tvAddress = itemView.tvAddress
        private val tvPrice = itemView.tvPrice
        private val ivCarType = itemView.ivCarType
        private val tvDetails = itemView.tvDetails

        fun bind(journey: Journey, clickListener: (Journey) -> Unit) = with(journey) {
            val dateTime = "$date, $pickUpTime"
            tvDate?.text = dateTime
            tvAddress?.text = dropOffPoint
            tvPrice?.text = total
            ivCarType?.setImageResource(carIcon)
            tvDetails?.visible()
            itemView.setOnClickListener { clickListener(journey) }
        }
    }
}