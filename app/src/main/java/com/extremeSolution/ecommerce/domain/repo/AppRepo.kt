package com.extremeSolution.ecommerce.domain.repo

import com.extremeSolution.ecommerce.domain.models.product.Product
import retrofit2.Response

interface AppRepo {
    suspend fun getCategoriesList(): Response<List<String>>

    suspend fun getAllProducts(): Response<List<Product>>
}