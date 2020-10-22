package com.example.data2.service

import com.example.data2.response.ListLineBusResponse
import com.example.data2.response.RecorridoBaseResponse
import com.example.domain.response.Coordinates
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceApi {
    @GET("/RecorridoBase/ObtenerRecorridoBase")
    fun getServiceBaseRouteInformation(
        @Query("linea") linea: String
    ): Call<List<RecorridoBaseResponse>>

    @GET("/Colectivo/ObtenerRecorridos")
    fun getListOfBuses(): Call<List<ListLineBusResponse>>


    @POST("/CalculadorTiempo/ObtenerTiempoPorRegresionAcumulado")
    fun calcularTiempoPorRegresionAcumulado(
        @Body  user:TravelEstimate
    ): Call<Double>


    @POST("/CalculadorTiempo/ObtenerTiempoPorRegresionDiferenciaDeCeldas")
    fun calcularTiempoPorRegresionDiferenciaDeCeldas(
        @Body  user:TravelEstimate
    ): Call<Double>


    @POST("/CalculadorTiempo/ObtenerTiempoEntreCoordenadasComplejo")
    fun calcularTiempoEntreCoordenadasComplejo(
        @Body  user:TravelEstimate
    ): Call<Double>
}