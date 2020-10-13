package com.example.baseproyect.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.baseproyect.R
import kotlinx.android.synthetic.main.fragment_ride_data.*

class FragmentTravelPrediction : Fragment() {

    //    private val cardInformation by lazy { fragment_card_info }
    private val cardMainTitleText by lazy { fragment_card_title_text_view }
    private val cardDistanceText by lazy { fragment_card_distance_text }
    private val cardDistanceTextValue by lazy { fragment_card_distance_text_value }
    private val cardTimeText by lazy { fragment_card_time_text }
    private val cardTimeTextValue by lazy { fragment_card_time_text_value }

    private val cardImageClose by lazy { fragment_card_image_close }
    private val cardImageHistory by lazy { fragment_card_image_history }
    private var latitud: Double = 0.0
    private var longuitud: Double = 0.0

    companion object {
        fun newInstance(lat: Double, lng: Double): FragmentTravelPrediction {

            val fragment = FragmentTravelPrediction()

            val args = Bundle()
            args.putDouble("EXTRA_VAL_LAT", lat)
            args.putDouble("EXTRA_VAL_LONG", lng)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        latitud = requireArguments().getDouble("EXTRA_VAL_LAT")
        longuitud = requireArguments().getDouble("EXTRA_VAL_LONG")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardTimeTextValue.text = latitud.toString()
        cardDistanceTextValue.text = latitud.toString()
        cardImageClose.setOnClickListener {

            activity?.onBackPressed()
        }
        cardImageHistory.setOnClickListener {
            Toast.makeText(
                context, "History",
                Toast.LENGTH_LONG
            ).show()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ride_data, container, false)
    }
}