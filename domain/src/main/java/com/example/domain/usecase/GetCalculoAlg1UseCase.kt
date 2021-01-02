package com.example.domain.usecase

import com.example.domain.response.Coordinates
import com.example.domain.response.TravelBody
import com.example.domain.response.UseCaseResult
import com.example.domain.services.AlgoritmsService
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetCalculoAlgoritmosUseCase() : KoinComponent {

    private val getAlgoritmsServiceRepository: AlgoritmsService by inject()
    fun selectTypeAlgService(
        listOfParadas:  List<*>?,
        algoritm:String
    ) = when(algoritm){
        "RegresionAcumulado" -> {
            val lista = listOfParadas as List<*>
            getAlgoritmsServiceRepository.getCalcularTiempoPorRegresionAcumulado(
                listOfParadas
            )
        }
        "RegresionDiferenciaDeCeldas" -> getAlgoritmsServiceRepository.getCalcularTiempoPorRegresionDiferenciaDeCeldas(
            listOfParadas
        )
        "TiempoEntreCoordenadasComplejo"->getAlgoritmsServiceRepository.getCalcularTiempoEntreCoordenadasComplejo(
            listOfParadas
        )
        else -> {UseCaseResult.Success(TravelBody())}
//        "RegresionAcumulado"->getAlgoritmsServiceRepository.getCalcularTiempoPorRegresionAcumulado(
//            cordenadaO,
//            cordenadaD,
//            currentDateTimeString,
//            recorridoId,
//            lineaId,
//            unidadId
//        )
//        "RegresionDiferenciaDeCeldas"->getAlgoritmsServiceRepository.getCalcularTiempoPorRegresionDiferenciaDeCeldas(
//            cordenadaO,
//            cordenadaD,
//            currentDateTimeString,
//            recorridoId,
//            lineaId,
//            unidadId
//        )
//        "TiempoEntreCoordenadasComplejo"->getAlgoritmsServiceRepository.getCalcularTiempoEntreCoordenadasComplejo(
//            cordenadaO,
//            cordenadaD,
//            currentDateTimeString,
//            recorridoId,
//            lineaId,
//            unidadId
//        )
//        else -> {UseCaseResult.Success(TravelBody())}
    }

}