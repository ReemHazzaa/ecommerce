package com.extremeSolution.ecommerce.domain.usecases.local.cart.addProductToCart

import com.extremeSolution.ecommerce.domain.repo.AppRepo
import com.extremeSolution.ecommerce.domain.usecases.baseUseCase.BaseUseCase
import javax.inject.Inject

class AddProductToCartUseCase @Inject constructor(private val appRepo: AppRepo) :
    BaseUseCase<Int, Any?> {

    override suspend fun execute(params: Int?): Any {
        return appRepo.addProductToCart(productId = params!!)
    }

}