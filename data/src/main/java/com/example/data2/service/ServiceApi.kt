package com.example.data2.service

import com.example.data2.response.ListLineBusResponse
import com.example.data2.response.RecorridoBaseObjResponse
import com.example.data2.response.RecorridoBaseResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {
    @GET("/RecorridoBase/ObtenerRecorridoBase?linea=500")
    fun getServiceBaseRouteInformation(
//        @Path("linea") linea: Int
    ): Call<RecorridoBaseResponse>

    @GET("/Colectivo/ObtenerRecorridos")
    fun getListOfBuses(): Call<List<ListLineBusResponse>>
}