package com.extremeSolution.ecommerce.data.remote.apiService

import com.extremeSolution.ecommerce.domain.models.product.Product
import retrofit2.Response
import retrofit2.http.GET

interface FakeStoreApiService {

    @GET("products/categories")
    suspend fun getCategoriesList(): Response<List<String>>

    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>
}