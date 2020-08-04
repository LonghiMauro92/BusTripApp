package com.example.baseproyect.ui.fragments

import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baseproyect.ui.Event

class MapFragmentViewModel :
    ViewModel() {

     val mapMutableLiveData = MutableLiveData<Event<Data>>()

    val liveData: MutableLiveData<Event<Data>>
        get() {
            return mapMutableLiveData
        }
    var imageOpenButton: Int = 0
    var imageCloseButton: Int = 0
    var visibleOptions: Boolean = false
    fun setLoading() {

        mapMutableLiveData.postValue(Event(Data(status = Status.LOADING)))

    }
    fun showBaseRoute() {

        mapMutableLiveData.postValue(Event(Data(status = Status.SHOW_ROUTES)))

    }
    private fun configureDrawablesButton(@DrawableRes drawable: Int, @DrawableRes drawable2: Int) {
        imageOpenButton = drawable
        imageCloseButton = drawable2
//        setIconFloatingButton(imageOpenButton)
    }
    data class Data(var status: Status, var data: String? = null, var error: Exception? = null)

    enum class Status {
        LOADING,
        SHOW_ROUTES,
        ERROR
    }
}