package com.example.baseproyect.di

import com.example.baseproyect.ui.fragments.FragmentTravelPredictionViewModel
import com.example.baseproyect.ui.fragments.MapFragmentViewModel
import com.example.baseproyect.ui.fragments.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val viewModelModule = module {
    viewModel { MapFragmentViewModel() }
    viewModel { SettingsViewModel() }
    viewModel { FragmentTravelPredictionViewModel(get()) }
}