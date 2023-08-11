package com.extremeSolution.ecommerce.data.repo

import com.extremeSolution.ecommerce.data.remote.apiService.FakeStoreApiService
import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.repo.AppRepo
import retrofit2.Response
import javax.inject.Inject

class AppRepoImpl @Inject constructor(
    private val apiService: FakeStoreApiService
) : AppRepo {
    override suspend fun getCategoriesList(): Response<List<String>> {
        return apiService.getCategoriesList()
    }

    override suspend fun getAllProducts(): Response<List<Product>> {
        return apiService.getAllProducts()
    }

    override suspend fun getProductsInCategory(category: String): Response<List<Product>> {
        return apiService.getProductsInCategory(category)
    }

    override suspend fun getProductDetails(productId: Int): Response<Product> {
        return apiService.getProductDetails(productId)
    }

}