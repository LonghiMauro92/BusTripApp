package com.example.data2.mapper

import com.example.data2.response.CoordinateResponse
import com.example.data2.response.RecorridoBaseResponse
import com.example.domain.response.Coordinates
import com.example.domain.response.RecorridoBaseInformation

//fun RecorridoBaseObjResponse.toRideInformation() =
//    RecorridoBaseObjInformation(recorridoIda.toInformation(), recorridoVuelta.toInformation())

fun RecorridoBaseResponse.toInformation() =
    RecorridoBaseInformation(
        recorridoId,
        linea,
        transformToListOfHouseDetail(coordenadas)
    )

fun transformToListOfHouseDetail(list: List<CoordinateResponse>): List<Coordinates> =
    list.map {
        Coordinates(it.lat, it.lng)
    }
//fun CoordinateResponse.toCoordinate() =
//    Coordinates(lat, lng)