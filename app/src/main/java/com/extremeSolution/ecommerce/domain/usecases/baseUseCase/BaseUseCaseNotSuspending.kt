package com.extremeSolution.ecommerce.domain.usecases.baseUseCase

interface BaseUseCaseNotSuspending<in Params, out Type> {
    fun execute(params: Params? = null): Type
}