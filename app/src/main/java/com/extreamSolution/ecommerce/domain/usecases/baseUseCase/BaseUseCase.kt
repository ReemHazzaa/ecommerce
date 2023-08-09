package com.extreamSolution.ecommerce.domain.usecases.baseUseCase

interface BaseUseCase<in Params, out Type> {
    suspend fun execute(params: Params? = null): Type
}