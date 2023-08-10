package com.extremeSolution.ecommerce.domain.usecases.productsInCategory

import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCase
import retrofit2.Response
import javax.inject.Inject

class GetProductsInCategoryUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCase<GetProductsInCategoryUseCase.Params, Response<List<Product>>> {

    data class Params(
        val category: String
    )

    override suspend fun execute(params: Params?): Response<List<Product>> {
        return appRepo.getProductsInCategory(params!!.category)
    }

}