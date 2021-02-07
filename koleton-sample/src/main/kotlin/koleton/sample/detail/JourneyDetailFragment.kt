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
import koleton.sample.databinding.FragmentJourneyDetailBinding
import koleton.sample.model.Journey
import koleton.sample.utils.DEFAULT_DELAY
import koleton.sample.utils.visible

class JourneyDetailFragment : AppCompatDialogFragment() {

    private val args: JourneyDetailFragmentArgs by navArgs()
    private val handler by lazy { Handler() }

    lateinit var binding: FragmentJourneyDetailBinding
    private val headerBinding get() = binding.clHeader
    private val bodyBinding get() = binding.clBody

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJourneyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivBack.setOnClickListener { dismiss() }
            tvToolbar.text = args.journey.date
            headerBinding.clHeader.loadSkeleton {
                shimmer(getCustomShimmer())
            }
        }
        getJourneyDetail()
    }

    private fun getJourneyDetail() {
        handler.postDelayed({ onSuccess() }, DEFAULT_DELAY)
    }

    private fun onSuccess() {
        bodyBinding.clBody.visible()
        headerBinding.clHeader.hideSkeleton()
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
        headerBinding.apply {
            ivMap.setImageResource(mapImage)
            tvPickUpValue.text = pickUpPoint
            tvPickUpTime.text = pickUpTime
            tvDropOffValue.text = dropOffPoint
            tvDropOffTime.text = dropOffTime
        }
        bodyBinding.apply {
            tvDriverName.text = driverName
            ivPicture.setImageResource(driverImage)
            tvCarName.text = carName
            ivCarType.setImageResource(carIcon)
            tvBasePriceValue.text = basePrice
            tvServicePriceValue.text = servicePrice
            tvTotalValue.text = total
            tvPaymentValue.text = paymentMethod
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}