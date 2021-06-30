package com.example.data2.response

import com.example.domain.response.Coordinates

class TravelEstimate(
    var posicionOrigen: Coordinates,
    var posicionDestino: Coordinates,
    var fecha: String,
    var trayecto: Int,
    var lineaId: Int,
    var unidadId: Int = 1
)