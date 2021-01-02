package com.example.domain.services

import com.example.domain.response.Coordinates
import com.example.domain.response.TravelBody
import com.example.domain.response.UseCaseResult

interface AlgoritmsService {
    fun getCalcularTiempoPorRegresionAcumulado(
        destination: Coordinates,
        cordenadaD: Coordinates,
        currentDateTimeString: String,
        recorridoId: String,
        lineaId: String,
        unidadId: String
    ): UseCaseResult<TravelBody>

    fun getCalcularTiempoPorRegresionDiferenciaDeCeldas(
        destination: Coordinates,
        cordenadaD: Coordinates,
        currentDateTimeString: String,
        recorridoId: String,
        lineaId: String,
        unidadId: String
    ): UseCaseResult<TravelBody>

    fun getCalcularTiempoEntreCoordenadasComplejo(
        destination: Coordinates,
        cordenadaD: Coordinates,
        currentDateTimeString: String,
        recorridoId: String,
        lineaId: String,
        unidadId: String
    ): UseCaseResult<TravelBody>

    fun getCalcularTiempoPorRegresionAcumulado(destination: List<*>?): UseCaseResult<List<TravelBody>>
    fun getCalcularTiempoPorRegresionDiferenciaDeCeldas(destination: List<*>?): UseCaseResult<List<TravelBody>>

    fun getCalcularTiempoEntreCoordenadasComplejo(destination: List<*>?): UseCaseResult<List<TravelBody>>
}