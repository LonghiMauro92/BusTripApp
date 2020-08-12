package com.example.di

import com.example.data2.NetworkingConfigHelper
import com.example.data2.impl.RideServiceImpl
import com.example.domain.services.RideService
import com.example.domain.usecase.GetBaseRoutesBusesUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single {
        GetBaseRoutesBusesUseCase()
    }
}

val repositoryModule = module {
    single<RideService> { RideServiceImpl() }
}

val networkingModule = module {
    single {
        NetworkingConfigHelper.createRetrofitInstance()
    }
}