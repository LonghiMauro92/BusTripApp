package com.example.baseproyect.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproyect.BaseViewModel
import com.example.baseproyect.ui.Address
import com.example.baseproyect.ui.Event
import com.example.domain.response.RecorridoBaseInformation
import com.example.domain.response.UseCaseResult
import com.example.domain.usecase.GetBaseRoutesBusesUseCase
import com.example.domain.usecase.GetLinesBusesUseCase
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


    lateinit var addressOrigin: Address
    lateinit var addressDestination: Address

    fun setLoading() {

        viewModelScope.launch {
            when (val result =
                withContext(Dispatchers.IO) { getLinesBusesUseCase.invoke() }) {
                is UseCaseResult.Failure -> {
//                Timber.e(CONNECT_TO_DRIVER_FAILED)
                    mapMutableLiveData.postValue(Event(Data(status = Status.ERROR, data = "service failed")))
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

    var listRecorridoIda = mutableListOf<RecorridoBaseInformation>()
    var listRecorridoVuelta = mutableListOf<RecorridoBaseInformation>()

    fun showBaseRoute(line: Int) {
        launch {
            when (val result =
                withContext(Dispatchers.IO) { getBaseRoutesBusesUseCase.invoke(line) }) {
                is UseCaseResult.Failure -> {
//                Timber.e(CONNECT_TO_DRIVER_FAILED)
                    mapMutableLiveData.postValue(Event(Data(status = Status.ERROR, data = "service failed")))
                }
                is UseCaseResult.Success -> {
                    val recorridoIda = RecorridoBaseInformation(
                        result.data[0].recorridoId,
                        result.data[0].linea,
                        result.data[0].coordenadas
                    )
                    val recorridoVuelta = RecorridoBaseInformation(
                        result.data[1].recorridoId,
                        result.data[1].linea,
                        result.data[1].coordenadas
                    )

                    listRecorridoIda.add(recorridoIda)
                    listRecorridoVuelta.add(recorridoVuelta)

                    mapMutableLiveData.postValue(
                        Event(
                            Data(
                                status = Status.SHOW_ROUTES,
                                data = listRecorridoIda,
                                dataAlternativa = listRecorridoVuelta
                            )
                        )
                    )
                }
            }
        }
    }

    private fun checkBothFields() {
        if (::addressOrigin.isInitialized && ::addressDestination.isInitialized) {
            if (addressOrigin.name.isNotEmpty() && addressDestination.name.isNotEmpty()) {
                mapMutableLiveData.value = Event(
                    Data(status = Status.ACTIVATE_BUTTON))
            } else {
                mapMutableLiveData.value = Event(
                    Data(
                        status = Status.DEACTIVATE_BUTTON))
            }
        } else {
            mapMutableLiveData.value = Event(
                Data(status = Status.DEACTIVATE_BUTTON))
        }
    }


    fun setManualOriginPoint(
        point: Marker,
        address: com.example.baseproyect.ui.Address
    ) {

        addressOrigin = address
        mapMutableLiveData.postValue(
            Event(
                Data(
                    status = Status.MANUAL_POINT,
                    data = address
                )
            )
        )
    }

    fun setManualDestPoint(
        point: Marker,
        address: com.example.baseproyect.ui.Address
    ) {

        addressDestination = address
        mapMutableLiveData.postValue(
            Event(
                Data(
                    status = Status.MANUAL_POINT,
                    data = address
                )
            )
        )

        checkBothFields()
    }
    fun proceedSearching() {

        mapMutableLiveData.postValue(
            Event(
                Data(
                    status = Status.PROCEED_SEARCHING,
                    data = addressOrigin,
                    dataAlternativa = addressDestination
                )
            )
        )
        addressOrigin=Address()
        addressDestination=Address()
        checkBothFields()
    }

    fun addMarker(marker: Marker) {
        listMarkers.add(marker)
    }

    fun cleanMarkers() {
        listMarkers.clear()
        addressOrigin=Address()
        addressDestination=Address()
        mapMutableLiveData.value = Event(
            Data(status = Status.DEACTIVATE_BUTTON))
    }

    data class Data(
        var status: Status,
        var data: Any? = null,
        var dataAlternativa: Any? = null,
        var error: Exception? = null
    )

    enum class Status {
        LOADING,
        ERROR,
        SHOW_ROUTES,
        MANUAL_POINT,
        PROCEED_SEARCHING,
        ACTIVATE_BUTTON,
        DEACTIVATE_BUTTON
    }
}