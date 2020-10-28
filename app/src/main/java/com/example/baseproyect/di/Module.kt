package com.example.baseproyect.di

import com.example.baseproyect.ui.fragments.FragmentTravelPredictionViewModel
import com.example.baseproyect.ui.fragments.MapFragmentViewModel
import com.example.baseproyect.ui.fragments.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MapFragmentViewModel() }
    viewModel { SettingsViewModel() }
    viewModel { FragmentTravelPredictionViewModel() }
}