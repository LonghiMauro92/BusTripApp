package com.example.data2.response

import com.google.gson.annotations.SerializedName

data class TravelBodyBEResponse (

    @SerializedName("tiempo")
    val tiempo: Double = 0.0,
    @SerializedName("distancia")
    val distancia: Double = 0.0
)