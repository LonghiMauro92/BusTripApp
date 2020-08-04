package com.example.data2

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkingConfigHelper {
    fun createRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
//            .baseUrl(getBaseUrl())  // agregar la url base de los servicios
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }
}