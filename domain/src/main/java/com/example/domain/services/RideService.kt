package com.example.domain.services

import com.example.domain.response.*

interface RideService {
    fun getLocalServideRideInformation( destination: Int): UseCaseResult<List<RecorridoBaseInformation>>
    fun getLinesInformation(): UseCaseResult<List<ListLineBus>>
    fun getRecorridoEntrePuntosSeleccionados(puntosSeleccionados: PositionMultipleLines): UseCaseResult<List<MultipleLinesTravelInfo>>
    fun getMultipleLinesSearching(puntosSeleccionados: PositionMultipleLines): UseCaseResult<List<MultipleLinesTravelInfo>>
}