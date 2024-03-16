package com.cs4520.assignment4.model.api

import com.cs4520.assignment4.model.api.Api.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductFactory {
    fun makeProductService(): ProductService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ProductService::class.java)
    }
}