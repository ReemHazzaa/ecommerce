package com.extremeSolution.ecommerce.domain.usecases.datastore.readCategories

import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCase
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCaseNotSuspending
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadCachedCategoriesUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCaseNotSuspending<Any?, Flow<List<String>>> {

    override fun execute(params: Any?): Flow<List<String>> {
        return appRepo.readCategoriesFromCache()
    }

}