package com.example.data2.impl

import com.example.data2.mapper.transformListTravelBodyBEResponseToListTravelBodyInformation
import com.example.data2.response.TravelEstimate
import com.example.data2.service.ServiceApi
import com.example.data2.service.ServiceGenerator
import com.example.domain.response.Coordinates
import com.example.domain.response.TravelBody
import com.example.domain.response.UseCaseResult
import com.example.domain.services.AlgoritmsService
import com.example.domain.usecase.InfoPuntoParadaDomain
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
    ): UseCaseResult<TravelBody> {

        val travelData = TravelEstimate(
            cordenadaO,
            cordenadaD,
            currentDateTimeString,
            recorridoId.toInt(),
            lineaId.toInt(),
            unidadId.toInt()
        )
//        val call =
//            api.createService(ServiceApi::class.java).calcularTiempoPorRegresionAcumulado(
//                travelData
//            )
//
//        try {
//            val response = call.execute()
//            if (response.isSuccessful) {
//                val body = response.body()
//                if (body != null) {
//                    return UseCaseResult.Success(body.toTravelBody())
//                } else {
//                    return UseCaseResult.Failure(Exception("failed"))
//                }
//            }
//        } catch (e: Exception) {
//            return UseCaseResult.Failure(e)
//        }
//
        return UseCaseResult.Failure(Exception("response not success"))
    }

    override fun getCalcularTiempoPorRegresionAcumulado(destination: List<*>?): UseCaseResult<List<TravelBody>> {
        val list = destination as List<*>
        val call =
            api.createService(ServiceApi::class.java).calcularTiempoPorRegresionAcumulado(
                list as List<InfoPuntoParadaDomain>
            )

        try {

            val response = call.execute()
            if (response.isSuccessful)
                response.body()?.let {
                    transformListTravelBodyBEResponseToListTravelBodyInformation(it)
                }?.let {
                    return UseCaseResult.Success(it)
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
    ): UseCaseResult<TravelBody> {

//        val travelData = TravelEstimate(
//            destination,
//            cordenadaD,
//            currentDateTimeString,
//            recorridoId.toInt(),
//            lineaId.toInt(),
//            unidadId.toInt()
//        )
//        val call =
//            api.createService(ServiceApi::class.java).calcularTiempoPorRegresionDiferenciaDeCeldas(
//                travelData
//            )
//
//        try {
//            val response = call.execute()
//            if (response.isSuccessful) {
//                val body = response.body()
//                if (body != null) {
//                    return UseCaseResult.Success(body.toTravelBody())
//                } else {
//                    return UseCaseResult.Failure(Exception("failed"))
//                }
//            }
//        } catch (e: Exception) {
//            return UseCaseResult.Failure(e)
//        }

        return UseCaseResult.Failure(Exception("response not success"))
    }

    override fun getCalcularTiempoPorRegresionDiferenciaDeCeldas(destination: List<*>?): UseCaseResult<List<TravelBody>> {
        val list = destination as List<InfoPuntoParadaDomain>

        val call =
            api.createService(ServiceApi::class.java).calcularTiempoPorRegresionDiferenciaDeCeldas(
                list
            )

        try {

            val response = call.execute()
            if (response.isSuccessful)
                response.body()?.let {
                    transformListTravelBodyBEResponseToListTravelBodyInformation(it)
                }?.let {
                    return UseCaseResult.Success(it)
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
    ): UseCaseResult<TravelBody> {

//        val travelData = TravelEstimate(
//            cordenadaO,
//            cordenadaD,
//            currentDateTimeString.toString(),
//            recorridoId.toInt(),
//            lineaId.toInt(),
//            unidadId.toInt()
//        )
//        val call =
//            api.createService(ServiceApi::class.java).calcularTiempoEntreCoordenadasComplejo(
//                travelData
//            )
//
//        try {
//            val response = call.execute()
//            if (response.isSuccessful) {
//                val body = response.body()
//                if (body != null) {
//                    return UseCaseResult.Success(body.toTravelBody())
//                } else {
//                    return UseCaseResult.Failure(Exception("failed"))
//                }
//            }
//        } catch (e: Exception) {
//            return UseCaseResult.Failure(e)
//        }

        return UseCaseResult.Failure(Exception("response not success"))
    }

    override fun getCalcularTiempoEntreCoordenadasComplejo(destination: List<*>?): UseCaseResult<List<TravelBody>> {
        val listSelected = destination as List<InfoPuntoParadaDomain>

        val call =
            api.createService(ServiceApi::class.java).calcularTiempoEntreCoordenadasComplejo(
                listSelected
            )

        try {

            val response = call.execute()
            if (response.isSuccessful)
                response.body()?.let {
                    transformListTravelBodyBEResponseToListTravelBodyInformation(it)
                }?.let {
                    return UseCaseResult.Success(it)
                }
        } catch (e: Exception) {
            return UseCaseResult.Failure(e)
        }

        return UseCaseResult.Failure(Exception("response not success"))
    }
}