package com.example.data2.response

import com.google.gson.annotations.SerializedName

class PositionRecorridoResponse(
    @SerializedName("recorridoId")
    val recorridoId: String = "",
    @SerializedName("coordenadasIntermedias")
    val coordenadasIntermedias: List<CoordinateResponse> = listOf()
)