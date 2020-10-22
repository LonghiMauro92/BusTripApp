package com.example.baseproyect.ui.fragments

import androidx.lifecycle.MutableLiveData
import com.example.baseproyect.BaseViewModel
import com.example.baseproyect.ui.Event
import org.koin.core.KoinComponent

class SettingsViewModel :
    BaseViewModel(), KoinComponent {

    val settingsMutableLiveData = MutableLiveData<Event<SettingsViewModel.Data>>()
    val liveData: MutableLiveData<Event<SettingsViewModel.Data>>
        get() {
            return settingsMutableLiveData
        }


    data class Data(
        var status: Status,
        var data: Any? = null,
        var error: Exception? = null
    )

    enum class Status {
        LOADING,
        ERROR,
    }
}