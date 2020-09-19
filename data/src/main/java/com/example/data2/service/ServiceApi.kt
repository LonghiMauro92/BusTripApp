package com.example.data2.service

import com.example.data2.response.ListLineBusResponse
import com.example.data2.response.RecorridoBaseObjResponse
import com.example.data2.response.RecorridoBaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {
    @GET("/RecorridoBase/ObtenerRecorridoBase")
    fun getServiceBaseRouteInformation(
        @Path("linea") linea: String
    ): Call<RecorridoBaseObjResponse>

    @GET("/Colectivo/ObtenerRecorridos")
    fun getListOfBuses(): Call<List<ListLineBusResponse>>
}