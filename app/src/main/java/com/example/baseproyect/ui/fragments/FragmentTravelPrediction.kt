package com.example.baseproyect.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.baseproyect.R
import com.example.baseproyect.ui.Event
import com.example.baseproyect.ui.PuntoSeleccion
import kotlinx.android.synthetic.main.fragment_ride_data.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FragmentTravelPrediction : Fragment() {

    //    private val cardInformation by lazy { fragment_card_info }
    private val cardMainTitleText by lazy { fragment_card_title_text_view }
    private val cardDistanceText by lazy { fragment_card_distance_text }
    private val cardDistanceTextValue by lazy { fragment_card_distance_text_value }
    private val cardTimeText by lazy { fragment_card_time_text }
    private val cardTimeTextValue by lazy { fragment_card_time_text_value }

    private val cardImageClose by lazy { fragment_card_image_close }
    private val cardImageHistory by lazy { fragment_card_image_history }
    private val loader by lazy { fragment_card_image_loading }
    private lateinit var puntoOrigin: PuntoSeleccion
    private lateinit var puntoDest: PuntoSeleccion
    private var linea: String = ""
    private var recorridoId: String = ""


    private val predictorViewModel by viewModel<FragmentTravelPredictionViewModel>()

    companion object {
        fun newInstance(
            address1: PuntoSeleccion,
            address2: PuntoSeleccion,
            linea: String,
            recorridoId: String
        ): FragmentTravelPrediction {

            val fragment = FragmentTravelPrediction()

            val args = Bundle()
            args.putSerializable("EXTRA_VAL_PUNTO_ORIGIN", address1 as PuntoSeleccion)
            args.putSerializable("EXTRA_VAL_PUNTO_DEST", address2 as PuntoSeleccion)
            args.putString("EXTRA_VAL_LINEA", linea)
            args.putString("EXTRA_VAL_RECORRIDO", recorridoId)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        puntoOrigin = requireArguments().getSerializable("EXTRA_VAL_PUNTO_ORIGIN") as PuntoSeleccion
        puntoDest = requireArguments().getSerializable("EXTRA_VAL_PUNTO_DEST") as PuntoSeleccion
        linea = requireArguments().getString("EXTRA_VAL_LINEA").toString()
        recorridoId = requireArguments().getString("EXTRA_VAL_RECORRIDO").toString()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        predictorViewModel.liveData.observe(::getLifecycle, ::updateUI)
        predictorViewModel.getCurrentPrediction(
            puntoOrigin,
            puntoDest,
            linea,
            recorridoId
        )


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

    private fun updateUI(data: Event<FragmentTravelPredictionViewModel.Data>) {
        val pokemonCardDetailData = data.getContentIfNotHandled()
        when (pokemonCardDetailData?.status) {
            FragmentTravelPredictionViewModel.Status.LOADING -> {
                setLoader()
            }

            FragmentTravelPredictionViewModel.Status.CONTENT_DATA -> {
                setUIValues(data.peekContent().data)
            }
        }
    }

    private fun setLoader() {
        loader.visibility = View.VISIBLE
        cardTimeTextValue.visibility = View.GONE
        cardDistanceTextValue.visibility = View.GONE
        cardDistanceText.visibility = View.GONE
        cardTimeText.visibility = View.GONE
    }

    fun setUIValues(data: Any?) {

        cardTimeTextValue.visibility = View.VISIBLE
        cardDistanceTextValue.visibility = View.VISIBLE
        cardDistanceText.visibility = View.VISIBLE
        cardTimeText.visibility = View.VISIBLE

        loader.visibility = View.GONE

        cardTimeTextValue.text = "79 seg"
        cardDistanceTextValue.text = "1000mts"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ride_data, container, false)
    }
}