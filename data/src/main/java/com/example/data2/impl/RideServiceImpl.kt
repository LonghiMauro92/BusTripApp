package com.example.data2.impl

import com.example.data2.mapper.BusLineMapper
import com.example.data2.mapper.transformListRecorridoBaseResponseToListRecorridoBaseInformation
import com.example.data2.mapper.transformListRecorridosMultipleLinesResponseToListRecorridoBaseInformation
import com.example.data2.mapper.transformPositionRecorridoResponseToRecorridoIntermedio
import com.example.data2.response.RecorridosMultipleLinesResponse
import com.example.domain.response.PositionRecorrido
import com.example.data2.service.ServiceApi
import com.example.data2.service.ServiceGenerator
import com.example.domain.response.*
import com.example.domain.services.RideService
import org.koin.core.KoinComponent

class RideServiceImpl : RideService, KoinComponent {

    private val api = ServiceGenerator()
    override fun getLocalServideRideInformation(destination: Int): UseCaseResult<List<RecorridoBaseInformation>> {
        val call =
            api.createService(ServiceApi::class.java)
                .getServiceBaseRouteInformation(destination.toString())

//        val mapper = RecorridoBaseMapper()
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return UseCaseResult.Success(
                        transformListRecorridoBaseResponseToListRecorridoBaseInformation(body)
                    )
                } else {
                    return UseCaseResult.Failure(Exception("failed"))
                }
            }
        } catch (e: Exception) {
            return UseCaseResult.Failure(e)
        }

        return UseCaseResult.Failure(Exception("response not success"))
    }

    override fun getLinesInformation(): UseCaseResult<List<ListLineBus>> {

        val call = api.createService(ServiceApi::class.java).getListOfBuses()

        val mapper = BusLineMapper()
        try {
            val response = call.execute()
            if (response.isSuccessful)
                response.body()?.let {
                    mapper.transformListOfBuses(it)
                }?.let {
                    return UseCaseResult.Success(it)
                }
        } catch (e: Exception) {
            return UseCaseResult.Failure(e)
        }
        return UseCaseResult.Failure(Exception(""))
    }


    override fun getRecorridoEntrePuntosSeleccionados(puntosSeleccionados: PositionMultipleLines): UseCaseResult<List<MultipleLinesTravelInfo>> {

        val call =
            api.createService(ServiceApi::class.java).getParadasCercanasMultiplesLineas(puntosSeleccionados)

        try {
            val response = call.execute()
            if (response.isSuccessful)
                response.body()?.let {
                    transformListRecorridosMultipleLinesResponseToListRecorridoBaseInformation(it)
                }?.let {
                    return UseCaseResult.Success(it)
                }
        } catch (e: Exception) {
            return UseCaseResult.Failure(e)
        }
        return UseCaseResult.Failure(Exception(""))
    }

    override fun getMultipleLinesSearching(destination: PositionMultipleLines): UseCaseResult<List<MultipleLinesTravelInfo>> {
        val call =
            api.createService(ServiceApi::class.java)
                .getParadasCercanasMultiplesLineas(destination)

        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return UseCaseResult.Success(
                        transformListRecorridosMultipleLinesResponseToListRecorridoBaseInformation(body)
                    )
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