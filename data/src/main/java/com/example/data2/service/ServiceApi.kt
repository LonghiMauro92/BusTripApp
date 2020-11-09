package com.example.data2.service

import com.example.data2.response.ListLineBusResponse
import com.example.data2.response.PositionRecorridoResponse
import com.example.data2.response.RecorridoBaseResponse
import com.example.domain.response.PositionRecorrido
import retrofit2.Call
import retrofit2.http.*

interface ServiceApi {
    @GET("/RecorridoBase/ObtenerRecorridoBase")
    fun getServiceBaseRouteInformation(
        @Query("linea") linea: String
    ): Call<List<RecorridoBaseResponse>>

    @GET("/Colectivo/ObtenerRecorridos")
    fun getListOfBuses(): Call<List<ListLineBusResponse>>


    @Headers( "Content-Type: application/json;charset=UTF-8")
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


    @POST("/RecorridoBase/CaminosAlternativos")
    fun calcularRecorridoEntreDosPuntosSeleccionados(
        @Body  puntosSeleccionados: PositionRecorrido
    ): Call<PositionRecorridoResponse>
}