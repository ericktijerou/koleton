package koleton.sample

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvUsers?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            loadSkeleton(R.layout.item_journey) {
                color(R.color.colorSkeleton)
            }
            val journeyList = DataSource.generateDataSet()
            adapter = JourneyListAdapter(journeyList) { journey -> onJourneyClick(journey) }
        }
        Handler().postDelayed({
            rvUsers?.hideSkeleton()
        }, 10000)
    }

    private fun onJourneyClick(journey: Journey) {
        Log.d("onClick", journey.address)
    }
}