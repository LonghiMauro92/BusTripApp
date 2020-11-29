package com.example.domain.usecase

import com.example.domain.response.PositionMultipleLines
import com.example.domain.services.RideService
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetListMultipleLinesTravelInfoUseCase : KoinComponent {

    private val getRideServiceRepository: RideService by inject()
    operator fun invoke(destination: PositionMultipleLines) = getRideServiceRepository.getMultipleLinesSearching( destination)
}