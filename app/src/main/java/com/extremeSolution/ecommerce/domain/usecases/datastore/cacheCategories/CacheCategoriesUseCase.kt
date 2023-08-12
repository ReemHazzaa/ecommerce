package com.extremeSolution.ecommerce.domain.usecases.datastore.cacheCategories

import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCase
import javax.inject.Inject

class CacheCategoriesUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCase<List<String>?, Any?> {

    override suspend fun execute(params: List<String>?): Any? {
        return appRepo.cacheCategories(params!!)
    }

}