package com.extremeSolution.ecommerce.data.remote.apiService

import com.extremeSolution.ecommerce.domain.models.product.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApiService {

    @GET("products/categories")
    suspend fun getCategoriesList(): Response<List<String>>

    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("products/category/{cName}")
    suspend fun getProductsInCategory(@Path("cName") categoryName: String): Response<List<Product>>

    @GET("products/category/{id}")
    suspend fun getProductDetails(@Path("id") productId: Int): Response<Product>
}