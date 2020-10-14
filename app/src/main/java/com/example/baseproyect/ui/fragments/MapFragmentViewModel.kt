package com.example.baseproyect.ui.fragments

import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproyect.BaseViewModel
import com.example.baseproyect.ui.Event
import com.example.domain.response.RecorridoBaseInformation
import com.example.domain.response.UseCaseResult
import com.example.domain.usecase.GetBaseRoutesBusesUseCase
import com.example.domain.usecase.GetLinesBusesUseCase
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class MapFragmentViewModel :
    BaseViewModel(), KoinComponent {

    private val getBaseRoutesBusesUseCase: GetBaseRoutesBusesUseCase by inject()
    private val getLinesBusesUseCase: GetLinesBusesUseCase by inject()

    val mapMutableLiveData = MutableLiveData<Event<Data>>()
    val liveData: MutableLiveData<Event<Data>>
        get() {
            return mapMutableLiveData
        }
    var imageOpenButton: Int = 0
    var imageCloseButton: Int = 0
    var visibleOptions: Boolean = false
    val listMarkers = mutableListOf<Marker>()
    fun setLoading() {

        viewModelScope.launch {
            when (val result =
                withContext(Dispatchers.IO) { getLinesBusesUseCase.invoke() }) {
                is UseCaseResult.Failure -> {
//                Timber.e(CONNECT_TO_DRIVER_FAILED)
//                mutableStatusLiveData.postValue(LiveDataEvent(PassengerMenuStatusLiveData(PassengerMenuRideStatus.NO_CABBIE)))
                }
                is UseCaseResult.Success -> {

                    mapMutableLiveData.postValue(
                        Event(
                            Data(
                                status = Status.LOADING,
                                data = result.data
                            )
                        )
                    )
                }
            }
        }
    }

    fun showBaseRoute(line: String) {
        launch {
            when (val result =
                withContext(Dispatchers.IO) { getBaseRoutesBusesUseCase.invoke(line) }) {
                is UseCaseResult.Failure -> {
//                Timber.e(CONNECT_TO_DRIVER_FAILED)
//                mutableStatusLiveData.postValue(LiveDataEvent(PassengerMenuStatusLiveData(PassengerMenuRideStatus.NO_CABBIE)))
                }
                is UseCaseResult.Success -> {
                    val listLatLng = mutableListOf<RecorridoBaseInformation>()
                    val recorridoIda = RecorridoBaseInformation(
                        result.data.recorridoIda.recorridoId,
                        result.data.recorridoIda.linea,
                        result.data.recorridoIda.coordenadas
                    )
                    val recorridoVuelta = RecorridoBaseInformation(
                        result.data.recorridoVuelta.recorridoId,
                        result.data.recorridoVuelta.linea,
                        result.data.recorridoVuelta.coordenadas
                    )
                    listLatLng.add(recorridoIda)
                    listLatLng.add(recorridoVuelta)
                    mapMutableLiveData.postValue(
                        Event(
                            Data(
                                status = Status.SHOW_ROUTES,
                                data = listLatLng
                            )
                        )
                    )
                }
            }
        }
    }

    private fun configureDrawablesButton(@DrawableRes drawable: Int, @DrawableRes drawable2: Int) {
        imageOpenButton = drawable
        imageCloseButton = drawable2
//        setIconFloatingButton(imageOpenButton)
    }

    fun addMarker(marker: Marker) {
        listMarkers.add(marker)
    }

    fun cleanMarkers() {
        listMarkers.clear()
    }
    data class Data(
        var status: Status,
        var data: Any? = null,
        var error: Exception? = null
    )

    enum class Status {
        LOADING,
        SHOW_ROUTES,
        ERROR
    }
}