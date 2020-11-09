package com.example.domain.usecase

import com.example.domain.response.PositionRecorrido
import com.example.domain.services.RideService
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetRecorridoEntrePuntosSeleccionados : KoinComponent {

    private val getRideServiceRepository: RideService by inject()
    operator fun invoke(puntosSeleccionados: PositionRecorrido) = getRideServiceRepository.getRecorridoEntrePuntosSeleccionados(puntosSeleccionados)
}