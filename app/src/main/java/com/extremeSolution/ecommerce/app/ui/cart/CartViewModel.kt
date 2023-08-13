package com.extremeSolution.ecommerce.app.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.usecases.local.cart.readCart.ReadCartUseCase
import com.extremeSolution.ecommerce.domain.usecases.local.cart.removeProductFromCart.RemoveProductFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    readCartUseCase: ReadCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase
): ViewModel() {

    fun calculateTotal(cart: List<Product>): String {
        val total = mutableListOf<Float>()
        var totalString = ""
        cart.forEach { cartItem ->
            try {
                total.add(cartItem.price.toFloat())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            totalString = buildString {
                append("Total: ")
                append(total.sum())
            }
        }
        return totalString
    }

    /** CACHE */
    val cart: LiveData<List<Product>> = readCartUseCase.execute().asLiveData()

    fun removeProductFromCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            removeProductFromCartUseCase.execute(product.id)
        }
    }

    fun emptyCart(list: List<Product>) {
        viewModelScope.launch(Dispatchers.IO) {
            for(product in list) {
                removeProductFromCartUseCase.execute(product.id)
            }
        }
    }
}