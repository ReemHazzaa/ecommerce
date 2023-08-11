package com.extremeSolution.ecommerce.domain.usecases.productDetails

import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCase
import retrofit2.Response
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCase<GetProductDetailsUseCase.Params, Response<Product>> {

    data class Params(
        val productId: Int
    )

    override suspend fun execute(params: Params?): Response<Product> {
        return appRepo.getProductDetails(params!!.productId)
    }

}