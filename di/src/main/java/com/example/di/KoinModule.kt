package com.example.di

import com.example.data2.NetworkingConfigHelper
import org.koin.dsl.module

val useCasesModule = module {
    single {
//        AckNotificationReceivedUseCase() ... ejemplo
    }
}

val networkingModule = module {
    single {
        NetworkingConfigHelper.createRetrofitInstance()
    }
}