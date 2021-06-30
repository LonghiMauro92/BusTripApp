package com.example.domain.usecase

import com.example.domain.response.TravelBody
import com.example.domain.response.UseCaseResult
import com.example.domain.services.AlgoritmsService
import org.koin.core.KoinComponent

class ExecuteTypeAlgorithmUseCase(private val getAlgorithmsServiceRepository: AlgoritmsService) :
    KoinComponent {

    fun selectTypeAlgService(
        listOfParadas: List<*>?,
        algoritm: String
    ) = when (algoritm) {
        "RegresionAcumulado" -> {
            listOfParadas as List<*>
            getAlgorithmsServiceRepository.getCalcularTiempoPorRegresionAcumulado(
                listOfParadas
            )
        }
        "RegresionDiferenciaDeCeldas" -> getAlgorithmsServiceRepository.getCalcularTiempoPorRegresionDiferenciaDeCeldas(
            listOfParadas
        )
        "TiempoEntreCoordenadasComplejo" -> getAlgorithmsServiceRepository.getCalcularTiempoEntreCoordenadasComplejo(
            listOfParadas
        )
        else -> {
            UseCaseResult.Success(TravelBody())
        }
    }

}