package com.example.baseproyect.ui.fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproyect.BaseViewModel
import com.example.baseproyect.ui.Event
import com.example.baseproyect.ui.PuntoSeleccion
import com.example.domain.response.Coordinates
import com.example.domain.response.UseCaseResult
import com.example.domain.usecase.GetCalculoAlgoritmosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

class FragmentTravelPredictionViewModel :
    BaseViewModel(), KoinComponent {

    private val getCalculoAlgSimpleUseCase: GetCalculoAlgoritmosUseCase by inject()

    lateinit var algorithm:String

    val predictionMutableLiveData = MutableLiveData<Event<Data>>()
    val liveData: MutableLiveData<Event<Data>>
        get() {
            return predictionMutableLiveData
        }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentPrediction(
        puntoOrigin: PuntoSeleccion,
        puntoDest: PuntoSeleccion,
        linea: String,
        recorridoId: String,
        algorithmValue:String
    ) {


        predictionMutableLiveData.postValue(
            Event(
                Data(
                    status = Status.LOADING
                )
            )
        )
        algorithm = algorithmValue

        val date = Calendar.getInstance().time
        val dateInString = date.toString("yyyy-MM-dd'T'HH:mm:ss")

        val coordA = Coordinates()
        val coordB = Coordinates()
        puntoOrigin.address?.let {
            coordA.latitude = it.latitude!!
            coordA.longitude = it.longitude!!
        }
        puntoDest.address?.let {
            coordB.latitude = it.latitude!!
            coordB.longitude = it.longitude!!
        }
        viewModelScope.launch {
            when (val result =
                withContext(Dispatchers.IO) {
                    getCalculoAlgSimpleUseCase.selectTypeAlgService(
                        coordA,
                        coordB,
                        dateInString,
                        recorridoId,
                        linea,
                        recorridoId,
                        algorithmValue
                    )
                }) {
                is UseCaseResult.Failure -> {
                    predictionMutableLiveData.postValue(
                        Event(
                            Data(
                                status = Status.ERROR,
                                data = result.exception.message,
                                dataAlternativa = "Back"
                            )
                        )
                    )
                }
                is UseCaseResult.Success -> {
                    predictionMutableLiveData.postValue(
                        Event(
                            Data(
                                status = Status.CONTENT_DATA,
                                data = result.data
                            )
                        )
                    )
                }
            }
        }
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    data class Data(
        var status: Status,
        var data: Any? = null,
        var dataAlternativa: Any? = null,
        var error: Exception? = null
    )

    enum class Status {
        LOADING,
        CONTENT_DATA,
        ERROR,
    }
}