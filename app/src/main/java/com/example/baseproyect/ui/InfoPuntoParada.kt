package com.example.baseproyect.ui

import com.example.data2.service.TravelEstimate
import com.example.domain.response.Coordinates
import com.example.domain.usecase.InfoPuntoParadaDomain
import java.io.Serializable

class InfoPuntoParada (
    var posicionOrigen: Coordinates,
    var addressOrigen: com.example.baseproyect.ui.Address,
    var posicionDestino: Coordinates,
    var addressDestino: com.example.baseproyect.ui.Address,
    var fecha: String,
    var trayecto: Int,
    var lineaId: Int,
    var unidadId: Int = 1 ): Serializable

fun InfoPuntoParada.toDomainModel(): InfoPuntoParadaDomain =
    InfoPuntoParadaDomain(
        posicionOrigen = this.posicionOrigen,
        posicionDestino = this.posicionDestino,
        fecha = this.fecha,
        trayecto = this.trayecto,
        lineaId = this.lineaId,
        unidadId = this.unidadId
    )
