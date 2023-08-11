package com.extremeSolution.ecommerce.domain.usecases.local.cacheProduct

import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCase
import javax.inject.Inject

class CacheProductUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCase<Product, Any?> {

    override suspend fun execute(params: Product?): Any {
        return appRepo.insertProductToDB(params!!)
    }

}