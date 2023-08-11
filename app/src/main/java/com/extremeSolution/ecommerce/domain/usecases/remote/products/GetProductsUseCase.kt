package com.extremeSolution.ecommerce.domain.usecases.remote.products

import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCase
import retrofit2.Response
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCase<Any, Response<List<Product>>> {
    override suspend fun execute(params: Any?): Response<List<Product>> {
        return appRepo.getAllProducts()
    }

}