package com.example.domain.usecase

import com.example.domain.response.Coordinates
import com.example.domain.response.UseCaseResult
import com.example.domain.services.AlgoritmsService
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetCalculoAlgoritmosUseCase() : KoinComponent {

    private val getAlgoritmsServiceRepository: AlgoritmsService by inject()
    fun invokeAlgSimple(
        cordenadaO: Coordinates,
        cordenadaD: Coordinates,
        currentDateTimeString: String,
        recorridoId: String,
        lineaId: String,
        unidadId: String,
        algoritm:String
    ) = when(algoritm){
        "1"->getAlgoritmsServiceRepository.getCalcularTiempoPorRegresionAcumulado(
            cordenadaO,
            cordenadaD,
            currentDateTimeString,
            recorridoId,
            lineaId,
            unidadId
        )
        "2"->getAlgoritmsServiceRepository.getCalcularTiempoPorRegresionDiferenciaDeCeldas(
            cordenadaO,
            cordenadaD,
            currentDateTimeString,
            recorridoId,
            lineaId,
            unidadId
        )
        else -> {UseCaseResult.Success(0.0)}
    }

}