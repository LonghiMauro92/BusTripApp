package com.example.data2.service

import com.example.domain.response.Coordinates

class TravelEstimate(
    var posicionOrigen: Coordinates,
    var posicionDestino: Coordinates,
    var fecha: String,
    var recorridoId: String,
    var lineaId: String,
    var unidadId: String = "" )