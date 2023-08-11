package com.extremeSolution.ecommerce.domain.usecases.local.deleteProductFromDB

import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCase
import javax.inject.Inject

class DeleteProductFromDBUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCase<Product, Any?> {

    override suspend fun execute(params: Product?): Any {
        return appRepo.deleteProductFromDB(params!!)
    }


}