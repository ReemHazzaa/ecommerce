package com.extreamSolution.ecommerce.data.remote.apiService

import retrofit2.http.GET

interface FakeStoreApiService {

    @GET("products/categories")
    suspend fun getCategoriesList(): List<String>
}