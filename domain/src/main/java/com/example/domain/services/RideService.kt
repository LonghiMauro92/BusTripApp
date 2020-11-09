package com.example.domain.services

import com.example.domain.response.*

interface RideService {
//    fun getRideInformation( destination: String): UseCaseResult<List<Coordinates>>
    fun getLocalServideRideInformation( destination: Int): UseCaseResult<List<RecorridoBaseInformation>>
    fun getLinesInformation(): UseCaseResult<List<ListLineBus>>
    fun getRecorridoEntrePuntosSeleccionados(puntosSeleccionados: PositionRecorrido): UseCaseResult<RecorridoIntermedio>
}