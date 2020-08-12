package com.example.domain.services

import com.example.domain.response.Coordinates
import com.example.domain.response.UseCaseResult

interface RideService {
    fun getRideInformation( destination: String): UseCaseResult<List<Coordinates>>
}