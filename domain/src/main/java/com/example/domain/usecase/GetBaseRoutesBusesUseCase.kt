package com.example.domain.usecase

import com.example.domain.services.RideService
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetBaseRoutesBusesUseCase : KoinComponent {

    private val getRideServiceRepository: RideService by inject()
    operator fun invoke(destination: Int) = getRideServiceRepository.getLocalServideRideInformation( destination)
}