package com.example.baseproyect.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproyect.BaseViewModel
import com.example.baseproyect.ui.Address
import com.example.baseproyect.ui.Event
import com.example.domain.response.Coordinates
import com.example.domain.response.RecorridoBaseInformation
import com.example.domain.response.UseCaseResult
import com.example.domain.usecase.GetBaseRoutesBusesUseCase
import com.example.domain.usecase.GetLinesBusesUseCase
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    var checkLocation: Boolean = true
    val listMarkers = mutableListOf<Marker>()
    val listSimulateBusMarkers = mutableListOf<Marker>()

    var activeLine: String = ""
    var activeAlgorithm: String = ""


    lateinit var addressOrigin: Address
    lateinit var addressDestination: Address

    fun setLoading() {

        viewModelScope.launch {
            when (val result =
                withContext(Dispatchers.IO) { getLinesBusesUseCase.invoke() }) {
                is UseCaseResult.Failure -> {
                    mapMutableLiveData.postValue(
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
    var listActiveBusRecA = mutableListOf<Coordinates>()
    var listRecorridoVuelta = mutableListOf<RecorridoBaseInformation>()
    var listActiveBusRecB = mutableListOf<Coordinates>()

    fun showBaseRoute(line: Int) {
        activeLine = line.toString()

        if (listRecorridoIda.isNotEmpty() && listActiveBusRecB.isNotEmpty()) {
            listRecorridoIda.clear()
            listActiveBusRecA.clear()
            listRecorridoVuelta.clear()
            listActiveBusRecB.clear()
        }

        launch {
            when (val result =
                withContext(Dispatchers.IO) { getBaseRoutesBusesUseCase.invoke(line) }) {
                is UseCaseResult.Failure -> {
                    mapMutableLiveData.postValue(
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
                    selectActiveBus(listRecorridoIda, listRecorridoVuelta)

                    checkLocation = true
                    showAutoLocation()
                }
            }
        }
    }

    private fun selectActiveBus(
        recorridoIda: MutableList<RecorridoBaseInformation>,
        recorridoVuelta: MutableList<RecorridoBaseInformation>
    ) {
        listActiveBusRecA.addAll(recorridoIda[0].coordenadas)
        listActiveBusRecB.addAll(recorridoVuelta[0].coordenadas)
    }

    private fun checkBothFields() {
        if (::addressOrigin.isInitialized && ::addressDestination.isInitialized) {
            if (addressOrigin.name.isNotEmpty() && addressDestination.name.isNotEmpty()) {
                mapMutableLiveData.value = Event(
                    Data(status = Status.ACTIVATE_BUTTON)
                )
            } else {
                mapMutableLiveData.value = Event(
                    Data(
                        status = Status.DEACTIVATE_BUTTON
                    )
                )
            }
        } else {
            mapMutableLiveData.value = Event(
                Data(status = Status.DEACTIVATE_BUTTON)
            )
        }
    }


    fun setManualOriginPoint(
        address: Address
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
        address: Address
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
        checkLocation = false
        mapMutableLiveData.postValue(
            Event(
                Data(
                    status = Status.PROCEED_SEARCHING,
                    data = addressOrigin,
                    dataAlternativa = addressDestination
                )
            )
        )
        addressOrigin = Address()
        addressDestination = Address()
        checkBothFields()
    }

    fun addMarker(marker: Marker) {
        listMarkers.add(marker)
    }

    fun addSimBusMarker(markerA: Marker, markerB: Marker) {
        listSimulateBusMarkers.clear()
        listSimulateBusMarkers.add(markerA)
        listSimulateBusMarkers.add(markerB)
    }

    fun cleanMarkers() {
        listMarkers.clear()
        addressOrigin = Address()
        addressDestination = Address()
        checkLocation=false
        mapMutableLiveData.postValue(
            Event(
                Data(status = Status.DEACTIVATE_BUTTON)
            )
        )
    }

    fun showAutoLocation() {
        if (checkLocation) {
            launch {
                withContext(Dispatchers.IO) {
                    delay(2500L) // retraso non-blocking de 2,5 segundos
                    if(listActiveBusRecA.isNotEmpty() || listActiveBusRecB.isNotEmpty()) {
                        mapMutableLiveData.postValue(
                            Event(
                                Data(
                                    status = Status.SHOW_LOC,
                                    data = listActiveBusRecA[0],
                                    dataAlternativa = listActiveBusRecB[0]
                                )
                            )
                        )
                        listActiveBusRecA.removeAt(0)
                        listActiveBusRecB.removeAt(0)
                    }else{
                        mapMutableLiveData.postValue(
                            Event(
                                Data(
                                    status = Status.ERROR,
                                    data = "error service",
                                    dataAlternativa = "Back"
                                )
                            )
                        )
                    }
                }
            }
        }
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
        DEACTIVATE_BUTTON,
        SHOW_LOC
    }
}