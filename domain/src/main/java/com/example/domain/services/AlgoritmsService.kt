package com.example.domain.services

import com.example.domain.response.Coordinates
import com.example.domain.response.UseCaseResult

interface AlgoritmsService  {
    fun getCalcularTiempoPorRegresionAcumulado(
        destination: Coordinates,
        cordenadaD: Coordinates,
        currentDateTimeString: String,
        recorridoId: String,
        lineaId: String,
        unidadId: String
    ): UseCaseResult<Double>

    fun getCalcularTiempoPorRegresionDiferenciaDeCeldas(
        destination: Coordinates,
        cordenadaD: Coordinates,
        currentDateTimeString: String,
        recorridoId: String,
        lineaId: String,
        unidadId: String
    ): UseCaseResult<Double>

    fun getCalcularTiempoEntreCoordenadasComplejo(
        destination: Coordinates,
        cordenadaD: Coordinates,
        currentDateTimeString: String,
        recorridoId: String,
        lineaId: String,
        unidadId: String
    ): UseCaseResult<Double>
}