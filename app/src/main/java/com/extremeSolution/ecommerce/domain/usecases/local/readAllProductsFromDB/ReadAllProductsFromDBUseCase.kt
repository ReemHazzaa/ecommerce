package com.extremeSolution.ecommerce.domain.usecases.local.readAllProductsFromDB

import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCaseNotSuspending
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAllProductsFromDBUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCaseNotSuspending<Any?, Flow<List<Product>>?> {

    override fun execute(params: Any?): Flow<List<Product>> {
        return appRepo.readAllProductsFromDB()
    }

}