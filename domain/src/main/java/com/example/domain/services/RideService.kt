package com.example.domain.services

import com.example.domain.response.*

interface RideService {
    fun getRideInformation( destination: String): UseCaseResult<List<Coordinates>>
    fun getLocalServideRideInformation( destination: String): UseCaseResult<RecorridoBaseObjInformation>
    fun getLinesInformation(): UseCaseResult<List<ListLineBus>>
}