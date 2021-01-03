package com.example.baseproyect.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.baseproyect.R
import com.example.baseproyect.adapter.TravelPredictionAdapter
import com.example.baseproyect.ui.Event
import com.example.baseproyect.ui.InfoPuntoParada
import com.example.baseproyect.ui.invokeAlertDialog
import com.example.domain.response.TravelBody
import kotlinx.android.synthetic.main.fragment_travel_prediction.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class FragmentTravelPrediction : Fragment() {

    private val cardInformation by lazy { fragment_card_info }
    private val cardLoader by lazy { fragment_card_image_loading }
    private val recyclerView by lazy { fragment_travel_prediction_recycler_view }

    private var algoritmo: String = ""
    private lateinit var listaPuntos: ArrayList<InfoPuntoParada>

    private val predictorViewModel by viewModel<FragmentTravelPredictionViewModel>()

    private var adapter: TravelPredictionAdapter? = null

    companion object {
        const val EXTRA_VALUE_LISTA_PUNTOS = "EXTRA_VALUE_LISTA_PUNTOS"
        const val EXTRA_VALUE_ALGORITHM = "EXTRA_VALUE_ALGORITHM"
        fun newInstance(
            lista: ArrayList<InfoPuntoParada>,
            algorithm: String
        ): FragmentTravelPrediction {

            val fragment = FragmentTravelPrediction()

            val args = Bundle()
            args.putSerializable(EXTRA_VALUE_LISTA_PUNTOS, lista)
            args.putString(EXTRA_VALUE_ALGORITHM, algorithm)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        listaPuntos =
            requireArguments().getSerializable(EXTRA_VALUE_LISTA_PUNTOS) as ArrayList<InfoPuntoParada>
        algoritmo = requireArguments().getString(EXTRA_VALUE_ALGORITHM).toString()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        predictorViewModel.liveData.observe(::getLifecycle, ::updateUI)

        predictorViewModel.getCurrentPrediction(
            listaPuntos, algoritmo
        )

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
            FragmentTravelPredictionViewModel.Status.ERROR -> {
                invokeAlertDialog(
                    activity = requireActivity(),
                    message = data.peekContent().data.toString(),
                    positiveButtonS = data.peekContent().dataAlternativa.toString()
                )
            }

        }
    }

    private fun setLoader() {
        cardLoader.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun setUIValues(data: Any?) {

        cardLoader.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

        val values = data as List<TravelBody>

        adapter = TravelPredictionAdapter(values, algoritmo)

        recyclerView.adapter = adapter
        adapter?.listener = {
            onCloseClick()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_travel_prediction, container, false)
    }

    fun onCloseClick() {
        cardInformation.visibility = View.GONE
    }
}