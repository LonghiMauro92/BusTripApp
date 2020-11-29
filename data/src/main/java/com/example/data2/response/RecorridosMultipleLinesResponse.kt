package com.example.data2.response

import com.google.gson.annotations.SerializedName

class RecorridosMultipleLinesResponse (
    @SerializedName("trayecto")
    val trayecto: String = "",
    @SerializedName("linea")
    val linea: String = "",
    @SerializedName("coordenadasIntermedias")
    val coordenadas: List<CoordinateResponse> = listOf())