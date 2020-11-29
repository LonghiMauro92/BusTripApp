package com.example.baseproyect.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproyect.BaseViewModel
import com.example.baseproyect.ui.Address
import com.example.baseproyect.ui.Event
import com.example.domain.response.*
import com.example.domain.usecase.GetBaseRoutesBusesUseCase
import com.example.domain.usecase.GetLinesBusesUseCase
import com.example.domain.usecase.GetListMultipleLinesTravelInfoUseCase
import com.example.domain.usecase.GetRecorridoEntrePuntosSeleccionados
import com.google.android.gms.maps.model.LatLng
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
    private val getRecorridoEntrePuntosSeleccionados: GetRecorridoEntrePuntosSeleccionados by inject()
    private val getRecorridoEntreLineasSeleccionadas: GetListMultipleLinesTravelInfoUseCase by inject()

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

    var activeLine: MutableList<Int> = mutableListOf(500) // Por defecto toma la linea 500
    var activeLineButtonFlag1: Boolean = false // Por defecto toma la linea 500
    var activeLineButtonFlag2: Boolean = false // Por defecto toma la linea 500
    var activeLineButtonFlag3: Boolean = false // Por defecto toma la linea 500
    var activeLineButtonFlag4: Boolean = false // Por defecto toma la linea 500
    var activeLineButtonFlag5: Boolean = false // Por defecto toma la linea 500

    var activeAlgorithm: String = "RegresionAcumulado" // Por defecto tomara el 1er algoritmo

    var myLocation: LatLng = LatLng(0.0, 0.0)

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
        activeLine = mutableListOf(line)

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

    fun checkBothFields() {
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
        checkBothFields()
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
        getRecorridoEntreDosPuntosSeleccionados(
            PositionMultipleLines(
                Coordinates(addressOrigin.latitude!!, addressOrigin.longitude!!),
                Coordinates(addressDestination.latitude!!, addressDestination.longitude!!),
                activeLine
            )
        )

//        mapMutableLiveData.postValue(
//            Event(
//                Data(
//                    status = Status.PROCEED_SEARCHING,
//                    data = addressOrigin,
//                    dataAlternativa = addressDestination
//                )
//            )
//        )
//        addressOrigin = Address()
//        addressDestination = Address()
//        checkBothFields()
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
        checkLocation = false
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
                    delay(4000L) // retraso non-blocking de 4 segundos
                    if (listActiveBusRecA.isNotEmpty() || listActiveBusRecB.isNotEmpty()) {
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
                    } else {
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

    fun getRecorridoEntreDosPuntosSeleccionados(puntosSeleccionados: PositionMultipleLines) {

        viewModelScope.launch {
            when (val result =
                withContext(Dispatchers.IO) {
                    getRecorridoEntrePuntosSeleccionados.invoke(
                        puntosSeleccionados
                    )
                }) {
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
                                status = Status.PROCEED_SEARCHING,
                                data = result.data,
                                dataAlternativa = addressOrigin,
                                extraData = addressDestination
                            )
                        )
                    )

                    addressOrigin = Address()
                    addressDestination = Address()
                    checkBothFields()
                }
            }
        }
    }

    fun getRecorridoEntreDosLineasSeleccionadas(puntosSeleccionados: PositionMultipleLines) {

        viewModelScope.launch {
            when (val result =
                withContext(Dispatchers.IO) {
                    getRecorridoEntreLineasSeleccionadas.invoke(
                        puntosSeleccionados
                    )
                }) {
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
                                status = Status.PROCEED_SEARCHING,
                                data = result.data,
                                dataAlternativa = addressOrigin,
                                extraData = addressDestination
                            )
                        )
                    )

                }
            }
        }
    }

    fun setGoToButton() {
    if(activeLineButtonFlag1 || activeLineButtonFlag2 || activeLineButtonFlag3 || activeLineButtonFlag4){
            mapMutableLiveData.postValue(
                Event(
                    Data(
                        status = Status.ENABLE_GOTO_BUTTON
                    )
                )
            )
        }else{mapMutableLiveData.postValue(
        Event(
            Data(
                status = Status.DISABLE_GOTO_BUTTON
            )
        )
    )}
    }

    data class Data(
        var status: Status,
        var data: Any? = null,
        var dataAlternativa: Any? = null,
        var extraData: Any? = null,
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
        SHOW_LOC,
        ENABLE_GOTO_BUTTON,
        DISABLE_GOTO_BUTTON
    }
}