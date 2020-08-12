package com.example.baseproyect.ui.fragments

import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baseproyect.BaseViewModel
import com.example.baseproyect.ViewUtils.RECORRIDO_AZUL
import com.example.baseproyect.ui.Event
import com.example.domain.response.UseCaseResult
import com.example.domain.usecase.GetBaseRoutesBusesUseCase
import com.example.domain.usecase.GetLinesBusesUseCase
import com.google.android.gms.maps.model.LatLng
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
//    val mapMutableLiveDataString = MutableLiveData<Event<DataString>>()
//
//    val liveDataBusLine: MutableLiveData<Event<DataString>>
//        get() {
//            return mapMutableLiveDataString
//        }
    val liveData: MutableLiveData<Event<Data>>
        get() {
            return mapMutableLiveData
        }
    var imageOpenButton: Int = 0
    var imageCloseButton: Int = 0
    var visibleOptions: Boolean = false
    fun setLoading() {

        launch {
            when (val result =
                withContext(Dispatchers.IO) { getLinesBusesUseCase.invoke() }) {
                is UseCaseResult.Failure -> {
//                Timber.e(CONNECT_TO_DRIVER_FAILED)
//                mutableStatusLiveData.postValue(LiveDataEvent(PassengerMenuStatusLiveData(PassengerMenuRideStatus.NO_CABBIE)))
                }
                is UseCaseResult.Success -> {

                    mapMutableLiveData.postValue(Event(Data(status = Status.LOADING,data = result.data)))
                }
            }
        }
    }

    fun showBaseRoute() {
        launch {
            when (val result =
                withContext(Dispatchers.IO) { getBaseRoutesBusesUseCase.invoke("RECORRIDO_AZUL") }) {
                is UseCaseResult.Failure -> {
//                Timber.e(CONNECT_TO_DRIVER_FAILED)
//                mutableStatusLiveData.postValue(LiveDataEvent(PassengerMenuStatusLiveData(PassengerMenuRideStatus.NO_CABBIE)))
                }
                is UseCaseResult.Success -> {
                    val listLatLng = mutableListOf<LatLng>()
                    for (i in result.data) {
                        val lat = LatLng(i.lat, i.lng)
                        listLatLng.add(lat)
                    }
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

    data class Data(
        var status: Status,
        var data: Any? = null,
        var error: Exception? = null
    )
//
//    data class DataString(
//        var status: Status,
//        var data: MutableList<String>? = null,
//        var error: Exception? = null
//    )

    enum class Status {
        LOADING,
        SHOW_ROUTES,
        ERROR
    }
}