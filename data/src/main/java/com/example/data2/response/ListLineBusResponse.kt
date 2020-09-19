package com.example.data2.response

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class ListLineBusResponse (
    @SerializedName("id")
    val id: String = "",
    @SerializedName("base")
    val base: String = "",
    @SerializedName("linea")
    val linea: String = "")