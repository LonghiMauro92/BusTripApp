package com.example.data2.impl

import com.example.data2.mapper.BusLineMapper
import com.example.data2.mapper.toInformation
import com.example.data2.mapper.transformListRecorridoBaseResponseToListRecorridoBaseInformation
import com.example.data2.service.ServiceApi
import com.example.data2.service.ServiceGenerator
import com.example.domain.response.*
import com.example.domain.services.RideService
import org.koin.core.KoinComponent

class RideServiceImpl : RideService, KoinComponent {

    private val api = ServiceGenerator()
    override fun getLocalServideRideInformation(destination: Int): UseCaseResult<List<RecorridoBaseInformation>> {
        val call =
            api.createService(ServiceApi::class.java).getServiceBaseRouteInformation(destination.toString())

//        val mapper = RecorridoBaseMapper()
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return UseCaseResult.Success(transformListRecorridoBaseResponseToListRecorridoBaseInformation(body))
                } else {
                    return UseCaseResult.Failure(Exception("failed"))
                }
            }
        } catch (e: Exception) {
            return UseCaseResult.Failure(e)
        }

        return UseCaseResult.Failure(Exception("response not success"))
    }

//    override fun getRideInformation(
//        desination: String
//    ): UseCaseResult<List<Coordinates>> = UseCaseResult.Success(
//        listOf<Coordinates>(
//            Coordinates(-37.330472, -59.112383),
//            Coordinates(-37.331054, -59.113960),
//            Coordinates(-37.331626, -59.115398),
//            Coordinates(-37.332172, -59.1169),
//            Coordinates(-37.333339, -59.116171),
//            Coordinates(-37.333672, -59.116982),
//            Coordinates(-37.334126, -59.118186),
//            Coordinates(-37.333979, -59.118890),
//            Coordinates(-37.333776, -59.11992),
//            Coordinates(-37.333637, -59.120744),
//            Coordinates(-37.333905, -59.121425),
//            Coordinates(-37.334306, -59.122517),
//            Coordinates(-37.334732, -59.123605),
//            Coordinates(-37.335002, -59.124305),
//            Coordinates(-37.335552, -59.125878),
//            Coordinates(-37.335834, -59.126614),
//            Coordinates(-37.336117, -59.127351),
//            Coordinates(-37.336676, -59.128769),
//            Coordinates(-37.335534, -59.129494),
//            Coordinates(-37.334341, -59.130227),
//            Coordinates(-37.333097, -59.130930),
//            Coordinates(-37.331910, -59.131611),
//            Coordinates(-37.330733, -59.132330),
//            Coordinates(-37.329530, -59.133049),
//            Coordinates(-37.328363, -59.133712),
//            Coordinates(-37.328907, -59.135205),
//            Coordinates(-37.329389, -59.136522),
//            Coordinates(-37.329841, -59.137678),
//            Coordinates(-37.330383, -59.139166),
//            Coordinates(-37.32906, 59.139946),
//            Coordinates(-37.328506, -59.138515),
//            Coordinates(-37.328049, -59.137345),
//            Coordinates(-37.326868, -59.138064),
//            Coordinates(-37.325674, -59.138746),
//            Coordinates(-37.325179, -59.137399),
//            Coordinates(-37.324624, -59.135961),
//            Coordinates(-37.324069, -59.134475),
//            Coordinates(-37.323497, -59.133032),
//            Coordinates(-37.323196, -59.132222),
//            Coordinates(-37.322943, -59.131423),
//            Coordinates(-37.321718, -59.132136),
//            Coordinates(-37.321089, -59.132479),
//            Coordinates(-37.320460, -59.132823),
//            Coordinates(-37.319231, -59.133510),
//            Coordinates(-37.318053, -59.134243),
//            Coordinates(-37.317482, -59.13457),
//            Coordinates(-37.316912, -59.134911),
//            Coordinates(-37.315707, -59.135614),
//            Coordinates(-37.314512, -59.136303),
//            Coordinates(-37.313361, -59.136993),
//            Coordinates(-37.312271, -59.137602),
//            Coordinates(-37.311015, -59.138427),
//            Coordinates(-37.310929, -59.138250),
//            Coordinates(-37.310596, -59.137509),
//            Coordinates(-37.310255, -59.136695),
//            Coordinates(-37.309708, -59.135214),
//            Coordinates(-37.309167, -59.135896),
//            Coordinates(-37.308228, -59.137065),
//            Coordinates(-37.307349, -59.138192),
//            Coordinates(-37.306432, -59.139356),
//            Coordinates(-37.305518, -59.140527),
//            Coordinates(-37.304618, -59.141710),
//            Coordinates(-37.303707, -59.142855),
//            Coordinates(-37.302790, -59.141731),
//            Coordinates(-37.301845, -59.140580),
//            Coordinates(-37.300932, -59.139451),
//            Coordinates(-37.299997, -59.138319),
//            Coordinates(-37.299075, -59.137161),
//            Coordinates(-37.298141, -59.136035),
//            Coordinates(-37.297254, -59.137194),
//            Coordinates(-37.298151, -59.138341),
//            Coordinates(-37.299078, -59.139477),
//            Coordinates(-37.300015, -59.140620),
//            Coordinates(-37.300934, -59.141766),
//            Coordinates(-37.300369, -59.142495),
//            Coordinates(-37.299825, -59.143235),
//            Coordinates(-37.299140, -59.144066),
//            Coordinates(-37.298240, -59.145246),
//            Coordinates(-37.297318, -59.146391),
//            Coordinates(-37.296433, -59.147544),
//            Coordinates(-37.295808, -59.148339),
//            Coordinates(-37.295477, -59.148733),
//            Coordinates(-37.295057, -59.149315),
//            Coordinates(-37.294632, -59.149857),
//            Coordinates(-37.294111, -59.150528),
//            Coordinates(-37.293671, -59.151043),
//            Coordinates(-37.292771, -59.152198),
//            Coordinates(-37.291892, -59.153363),
//            Coordinates(-37.290983, -59.154522),
//            Coordinates(-37.290033, -59.155706),
//            Coordinates(-37.291008, -59.156845),
//            Coordinates(-37.291958, -59.157931),
//            Coordinates(-37.291098, -59.159077),
//            Coordinates(-37.290152, -59.160239),
//            Coordinates(-37.290786, -59.161003),
//            Coordinates(-37.291328, -59.161700),
//            Coordinates(-37.292028, -59.162489),
//            Coordinates(-37.292973, -59.163702)
//        )
//    )

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
}