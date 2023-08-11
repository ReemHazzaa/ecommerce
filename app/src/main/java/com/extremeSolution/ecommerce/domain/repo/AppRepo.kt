package com.extremeSolution.ecommerce.domain.repo

import com.extremeSolution.ecommerce.domain.models.product.Product
import retrofit2.Response

interface AppRepo {
    suspend fun getCategoriesList(): Response<List<String>>

    suspend fun getAllProducts(): Response<List<Product>>

    suspend fun getProductsInCategory(category: String): Response<List<Product>>

    suspend fun getProductDetails(productId: Int): Response<Product>
}