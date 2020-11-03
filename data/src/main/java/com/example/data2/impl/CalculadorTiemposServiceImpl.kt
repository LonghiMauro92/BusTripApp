package com.example.data2.impl

import com.example.data2.service.ServiceApi
import com.example.data2.service.ServiceGenerator
import com.example.data2.service.TravelEstimate
import com.example.domain.response.Coordinates
import com.example.domain.response.UseCaseResult
import com.example.domain.services.AlgoritmsService
import org.koin.core.KoinComponent

class CalculadorTiemposServiceImpl : AlgoritmsService, KoinComponent {

    private val api = ServiceGenerator()
    override fun getCalcularTiempoPorRegresionAcumulado(
        cordenadaO: Coordinates,
        cordenadaD: Coordinates,
        currentDateTimeString: String,
        recorridoId: String,
        lineaId: String,
        unidadId: String
    ): UseCaseResult<Double> {

        val travelData = TravelEstimate(
            cordenadaO,
            cordenadaD,
            currentDateTimeString,
            recorridoId.toInt(),
            lineaId.toInt(),
            unidadId.toInt()
        )
        val call =
            api.createService(ServiceApi::class.java).calcularTiempoPorRegresionAcumulado(
                travelData
            )

        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return UseCaseResult.Success(body)
                } else {
                    return UseCaseResult.Failure(Exception("failed"))
                }
            }
        } catch (e: Exception) {
            return UseCaseResult.Failure(e)
        }

        return UseCaseResult.Failure(Exception("response not success"))
    }

    override fun getCalcularTiempoPorRegresionDiferenciaDeCeldas(
        destination: Coordinates,
        cordenadaD: Coordinates,
        currentDateTimeString: String,
        recorridoId: String,
        lineaId: String,
        unidadId: String
    ): UseCaseResult<Double> {

        val travelData = TravelEstimate(
            destination,
            cordenadaD,
            currentDateTimeString,
            recorridoId.toInt(),
            lineaId.toInt(),
            unidadId.toInt()
        )
        val call =
            api.createService(ServiceApi::class.java).calcularTiempoPorRegresionDiferenciaDeCeldas(
                travelData
            )

        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return UseCaseResult.Success(body)
                } else {
                    return UseCaseResult.Failure(Exception("failed"))
                }
            }
        } catch (e: Exception) {
            return UseCaseResult.Failure(e)
        }

        return UseCaseResult.Failure(Exception("response not success"))
    }

    override fun getCalcularTiempoEntreCoordenadasComplejo(
        cordenadaO: Coordinates,
        cordenadaD: Coordinates,
        currentDateTimeString: String,
        recorridoId: String,
        lineaId: String,
        unidadId: String
    ): UseCaseResult<Double> {

        val travelData = TravelEstimate(
            cordenadaO,
            cordenadaD,
            currentDateTimeString.toString(),
            recorridoId.toInt(),
            lineaId.toInt(),
            unidadId.toInt()
        )
        val call =
            api.createService(ServiceApi::class.java).calcularTiempoEntreCoordenadasComplejo(
                travelData
            )

        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return UseCaseResult.Success(body)
                } else {
                    return UseCaseResult.Failure(Exception("failed"))
                }
            }
        } catch (e: Exception) {
            return UseCaseResult.Failure(e)
        }

        return UseCaseResult.Failure(Exception("response not success"))
    }
}