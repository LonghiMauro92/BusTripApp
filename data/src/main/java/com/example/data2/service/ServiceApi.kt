package com.example.data2.service

import com.example.data2.response.*
import com.example.domain.response.PositionMultipleLines
import com.example.domain.response.PositionRecorrido
import retrofit2.Call
import retrofit2.http.*

interface ServiceApi {
    @GET("/RecorridoBase/ObtenerRecorridosPorLinea")
    fun getServiceBaseRouteInformation(
        @Query("linea") linea: String
    ): Call<List<RecorridoBaseResponse>>

    @GET("/RecorridoBase/ObtenerRecorridos")
    fun getListOfBuses(): Call<List<ListLineBusResponse>>


    @Headers( "Content-Type: application/json;charset=UTF-8")
    @POST("/CalculadorTiempo/ObtenerTiempoPorRegresionAcumulado")
    fun calcularTiempoPorRegresionAcumulado(
        @Body  user:TravelEstimate
    ): Call<TravelBodyBEResponse>


    @POST("/CalculadorTiempo/ObtenerTiempoPorRegresionDiferenciaDeCeldas")
    fun calcularTiempoPorRegresionDiferenciaDeCeldas(
        @Body  user:TravelEstimate
    ): Call<TravelBodyBEResponse>


    @POST("/CalculadorTiempo/ObtenerTiempoEntreCoordenadasComplejo")
    fun calcularTiempoEntreCoordenadasComplejo(
        @Body  user:TravelEstimate
    ): Call<TravelBodyBEResponse>


    @POST("/RecorridoBase/CaminosAlternativos")
    fun calcularRecorridoEntreDosPuntosSeleccionados(
        @Body  puntosSeleccionados: PositionRecorrido
    ): Call<PositionRecorridoResponse>


    @POST("/RecorridoBase/ParadasCercanas")
    fun getParadasCercanasMultiplesLineas(
        @Body  puntosSeleccionados: PositionMultipleLines
    ): Call<List<RecorridosMultipleLinesResponse>>
}