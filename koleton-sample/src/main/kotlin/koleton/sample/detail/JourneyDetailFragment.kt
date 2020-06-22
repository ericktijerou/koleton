package koleton.sample.detail

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.fragment.navArgs
import com.facebook.shimmer.Shimmer
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import koleton.sample.R
import koleton.sample.model.Journey
import koleton.sample.utils.visible
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
        tvToolbar?.text = args.journey.date
        clHeader?.loadSkeleton {
            color(R.color.colorSkeleton)
            shimmer(getCustomShimmer())
        }
        getJourneyDetail()
    }

    private fun getJourneyDetail() {
        handler.postDelayed({ onSuccess() }, DELAY)
    }

    private fun onSuccess() {
        clBody?.visible()
        clHeader?.hideSkeleton()
        showInformation(args.journey)
    }

    private fun getCustomShimmer(): Shimmer {
        return Shimmer.AlphaHighlightBuilder()
            .setDirection(Shimmer.Direction.TOP_TO_BOTTOM)
            .setDuration(500)
            .setTilt(0f)
            .setBaseAlpha(0.4f)
            .setRepeatMode(ValueAnimator.INFINITE)
            .setRepeatDelay(1000)
            .setHighlightAlpha(0.15f)
            .setHeightRatio(1f)
            .setDropoff(1f)
            .build()
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

    companion object {
        const val DELAY: Long = 3000
    }
}