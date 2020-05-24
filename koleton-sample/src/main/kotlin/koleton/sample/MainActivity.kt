package koleton.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import koleton.api.loadSkeleton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvTitle.loadSkeleton()
        //tvTitle.hideSkeleton()
    }
}