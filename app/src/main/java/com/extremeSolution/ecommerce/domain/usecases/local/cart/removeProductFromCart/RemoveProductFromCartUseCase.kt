package com.extremeSolution.ecommerce.domain.usecases.local.cart.removeProductFromCart

import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCase
import javax.inject.Inject

class RemoveProductFromCartUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCase<Int, Any?> {

    override suspend fun execute(params: Int?): Any {
        return appRepo.removeProductFromCart(productId = params!!)
    }

}