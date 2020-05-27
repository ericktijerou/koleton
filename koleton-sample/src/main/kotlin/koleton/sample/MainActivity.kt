package koleton.sample

import android.os.Bundle
import android.os.Handler
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
            adapter = JourneyListAdapter(arrayOf())
        }
        Handler().postDelayed({
            rvUsers?.hideSkeleton()
        }, 10000)
    }
}