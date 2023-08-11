package com.extremeSolution.ecommerce.domain.usecases.remote.categories

import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCase
import retrofit2.Response
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCase<Any?, Response<List<String>>> {
    override suspend fun execute(params: Any?): Response<List<String>> {
        return appRepo.getCategoriesList()
    }

}