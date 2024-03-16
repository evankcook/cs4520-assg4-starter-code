package com.cs4520.assignment4.model.api

import com.cs4520.assignment4.model.Product
import com.cs4520.assignment4.model.api.Api.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET(ENDPOINT)
    suspend fun getProductList(): Response<List<Product>>
}