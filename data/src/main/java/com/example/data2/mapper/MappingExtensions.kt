package com.example.data2.mapper

import com.example.data2.response.RecorridoBaseObjResponse
import com.example.data2.response.RecorridoBaseResponse
import com.example.domain.response.Coordinates
import com.example.domain.response.RecorridoBaseInformation
import com.example.domain.response.RecorridoBaseObjInformation
import com.google.android.gms.maps.model.LatLng

fun RecorridoBaseObjResponse.toRideInformation() =
    RecorridoBaseObjInformation(recorridoIda.toInformation(), recorridoVuelta.toInformation())

fun RecorridoBaseResponse.toInformation() =
    RecorridoBaseInformation(recorridoId, linea, coordenadas)

