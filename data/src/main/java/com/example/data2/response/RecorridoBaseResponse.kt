package com.example.data2.response

import com.example.domain.response.Coordinates
import com.google.gson.annotations.SerializedName

    data class RecorridoBaseResponse(
    @SerializedName("recorridoId")
    val recorridoId: String = "",
    @SerializedName("linea")
    val linea: String = "",
    @SerializedName("coordenadas")
    val coordenadas: List<Coordinates> = listOf()
)