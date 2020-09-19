package com.example.data2.mapper

import com.example.data2.response.ListLineBusResponse
import com.example.domain.response.ListLineBus
class BusLineMapper : BaseMapper<ListLineBusResponse, ListLineBus> {
    override fun transform(type: ListLineBusResponse): ListLineBus = type.run {
        ListLineBus(
            id,
            base,
            linea
        )
    }

    fun transformListOfBuses(busResponse: List<ListLineBusResponse>) = busResponse.map { transform(it) }
}
