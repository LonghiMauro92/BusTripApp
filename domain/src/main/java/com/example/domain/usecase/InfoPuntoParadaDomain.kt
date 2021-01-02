package com.example.domain.usecase

import com.example.domain.response.Coordinates

class InfoPuntoParadaDomain (
    var posicionOrigen: Coordinates,
    var posicionDestino: Coordinates,
    var fecha: String,
    var trayecto: Int,
    var lineaId: Int,
    var unidadId: Int = 1 )

//fun InfoPuntoParadaDomain.toDomainModel(): TravelEstimate=
//    TravelEstimate(
//        posicionOrigen = this.posicionOrigen,
//        posicionDestino = this.posicionDestino,
//        fecha = this.fecha,
//        trayecto = this.trayecto,
//        lineaId = this.lineaId,
//        unidadId = this.unidadId
//    )
