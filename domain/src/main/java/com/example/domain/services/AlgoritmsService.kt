package com.example.domain.services

import com.example.domain.response.TravelBody
import com.example.domain.response.UseCaseResult

interface AlgoritmsService {

    fun getCalcularTiempoPorRegresionAcumulado(destination: List<*>?): UseCaseResult<List<TravelBody>>
    fun getCalcularTiempoPorRegresionDiferenciaDeCeldas(destination: List<*>?): UseCaseResult<List<TravelBody>>

    fun getCalcularTiempoEntreCoordenadasComplejo(destination: List<*>?): UseCaseResult<List<TravelBody>>
}