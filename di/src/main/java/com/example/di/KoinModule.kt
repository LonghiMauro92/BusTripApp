package com.example.di

import com.example.data2.NetworkingConfigHelper
import com.example.data2.impl.CalculadorTiemposServiceImpl
import com.example.data2.impl.RideServiceImpl
import com.example.domain.services.AlgoritmsService
import com.example.domain.services.RideService
import com.example.domain.usecase.*
import org.koin.dsl.module

val useCasesModule = module {
    single { GetBaseRoutesBusesUseCase() }
    single { GetLinesBusesUseCase() }
    single { GetCalculoAlgoritmosUseCase() }
    single { GetRecorridoEntrePuntosSeleccionados() }
    single { GetListMultipleLinesTravelInfoUseCase() }

}

val repositoryModule = module {
    single<RideService> { RideServiceImpl() }
    single<AlgoritmsService> { CalculadorTiemposServiceImpl() }
}

val networkingModule = module {
    single {
        NetworkingConfigHelper.createRetrofitInstance()
    }
}