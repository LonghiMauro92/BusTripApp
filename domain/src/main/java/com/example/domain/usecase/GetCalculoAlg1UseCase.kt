package com.example.domain.usecase

import com.example.domain.response.Coordinates
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
        unidadId: String
    ) =
        getAlgoritmsServiceRepository.getCalcularTiempoPorRegresionAcumulado(
            cordenadaO,
            cordenadaD,
            currentDateTimeString,
            recorridoId,
            lineaId,
            unidadId
        )

    fun invokeAlgComplejo(cordenadaO: Coordinates, cordenadaD: Coordinates,
                        currentDateTimeString: String, recorridoId:String, lineaId:String, unidadId:String) =
        getAlgoritmsServiceRepository.getCalcularTiempoPorRegresionDiferenciaDeCeldas(
            cordenadaO,
            cordenadaD,
            currentDateTimeString,
            recorridoId,
            lineaId,
            unidadId
        )
}