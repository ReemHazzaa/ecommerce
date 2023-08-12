package com.extremeSolution.ecommerce.domain.usecases.local.readProductFromDB

import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCaseNotSuspending
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadProductFromDBUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCaseNotSuspending<Int, Flow<Product>?> {

    override fun execute(params: Int?): Flow<Product> {
        return appRepo.readProductFromDB(params!!)
    }

}