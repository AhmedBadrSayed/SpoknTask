package com.mondiamedia.ahmedbadr.spokentask.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    fun getService(service: Class<*>): Any {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
    }

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}