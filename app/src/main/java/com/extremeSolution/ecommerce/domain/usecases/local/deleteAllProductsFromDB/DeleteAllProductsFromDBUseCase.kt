package com.extremeSolution.ecommerce.domain.usecases.local.deleteAllProductsFromDB

import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCase
import javax.inject.Inject

class DeleteAllProductsFromDBUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCase<Any, Any?> {

    override suspend fun execute(params: Any?): Any {
        return appRepo.deleteAllProductsFromDB()
    }

}