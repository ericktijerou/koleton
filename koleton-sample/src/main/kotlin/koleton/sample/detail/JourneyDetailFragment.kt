package koleton.sample.detail

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.fragment.navArgs
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import koleton.sample.R
import koleton.sample.model.Journey
import koleton.util.visible
import kotlinx.android.synthetic.main.fragment_journey_detail.*
import kotlinx.android.synthetic.main.include_journey_body.*
import kotlinx.android.synthetic.main.include_journey_header.*

class JourneyDetailFragment : AppCompatDialogFragment() {

    private val args: JourneyDetailFragmentArgs by navArgs()
    private val handler by lazy { Handler() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_journey_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivBack?.setOnClickListener { dismiss() }
        clHeader?.loadSkeleton { color(R.color.colorSkeleton) }
        tvToolbar?.text = args.journey.date
        handler.postDelayed({
            showInformation(args.journey)
            clBody?.visible()
            clHeader?.hideSkeleton()
        }, 1800)
    }

    private fun showInformation(journey: Journey) = with(journey) {
        ivMap?.setImageResource(mapImage)
        tvPickUpValue?.text = pickUpPoint
        tvPickUpTime?.text = pickUpTime
        tvDropOffValue?.text = dropOffPoint
        tvDropOffTime?.text = dropOffTime
        tvDriverName?.text = driverName
        ivPicture?.setImageResource(driverImage)
        tvCarName?.text = carName
        ivCarType?.setImageResource(carIcon)
        tvBasePriceValue?.text = basePrice
        tvServicePriceValue?.text = servicePrice
        tvTotalValue?.text = total
        tvPaymentValue?.text = paymentMethod
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}