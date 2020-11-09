package com.example.data2.mapper

import com.example.data2.response.CoordinateResponse
import com.example.data2.response.PositionRecorridoResponse
import com.example.data2.response.RecorridoBaseResponse
import com.example.domain.response.Coordinates
import com.example.domain.response.RecorridoBaseInformation
import com.example.domain.response.RecorridoIntermedio

fun transformListRecorridoBaseResponseToListRecorridoBaseInformation(list: List<RecorridoBaseResponse>): List<RecorridoBaseInformation> =
    list.map {
        it.toInformation()
    }
fun RecorridoBaseResponse.toInformation() =
    RecorridoBaseInformation(
        recorridoId,
        linea,
        transformListCoordinatesResponseToListCoordinates(coordenadas)
    )

fun transformListCoordinatesResponseToListCoordinates(list: List<CoordinateResponse>): List<Coordinates> =
    list.map {
        Coordinates(it.lat, it.lng)
    }

fun transformPositionRecorridoResponseToRecorridoIntermedio(response: PositionRecorridoResponse): RecorridoIntermedio =
    RecorridoIntermedio(
        recorridoId = response.recorridoId,
        coordenadasIntermedias = transformListCoordinatesResponseToListCoordinates(response.coordenadasIntermedias)
    )