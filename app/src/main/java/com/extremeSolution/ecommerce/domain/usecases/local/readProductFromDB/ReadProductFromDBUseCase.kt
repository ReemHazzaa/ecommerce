package com.extremeSolution.ecommerce.domain.usecases.local.readProductFromDB

import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCase
import javax.inject.Inject

class ReadProductFromDBUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCase<Int, Any?> {

    override suspend fun execute(params: Int?): Any {
        return appRepo.readProductFromDB(params!!)
    }

}