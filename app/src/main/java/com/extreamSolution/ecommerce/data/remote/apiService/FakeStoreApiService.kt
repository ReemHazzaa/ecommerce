package com.extreamSolution.ecommerce.data.remote.apiService

import com.extreamSolution.ecommerce.domain.models.product.Product
import retrofit2.http.GET

interface FakeStoreApiService {

    @GET("products/categories")
    suspend fun getCategoriesList(): List<String>

    @GET("products")
    suspend fun getAllProducts(): List<Product>
}