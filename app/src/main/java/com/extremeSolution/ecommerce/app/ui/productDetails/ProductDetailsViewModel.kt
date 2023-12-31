package com.extremeSolution.ecommerce.app.ui.productDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.extremeSolution.ecommerce.app.uiState.ErrorType
import com.extremeSolution.ecommerce.app.uiState.UiState
import com.extremeSolution.ecommerce.data.remote.networkLayer.NetworkManager
import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.usecases.local.cart.addProductToCart.AddProductToCartUseCase
import com.extremeSolution.ecommerce.domain.usecases.local.cart.readCart.ReadCartUseCase
import com.extremeSolution.ecommerce.domain.usecases.local.cart.removeProductFromCart.RemoveProductFromCartUseCase
import com.extremeSolution.ecommerce.domain.usecases.local.readProductFromDB.ReadProductFromDBUseCase
import com.extremeSolution.ecommerce.domain.usecases.remote.productDetails.GetProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productDetailsUseCase: GetProductDetailsUseCase,
    private val readProductFromDBUseCase: ReadProductFromDBUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val readCartUseCase: ReadCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    private val networkManager: NetworkManager
): ViewModel() {

    private var _productsResponse: MutableLiveData<UiState<Product>> = MutableLiveData()
    val productsResponse: LiveData<UiState<Product>> = _productsResponse

    fun getProductDetailsSafeCall(id: Int) {
        if (networkManager.isNetworkAvailable()) {
            getProductDetails(id)
        } else {
            _productsResponse.value = UiState.Error(ErrorType.API_ERROR)
        }
    }

    private fun getProductDetails(id: Int) = viewModelScope.launch {
        _productsResponse.value = UiState.Loading()
        try {
            val response = productDetailsUseCase.execute(GetProductDetailsUseCase.Params(id))
            _productsResponse.value = handleResponse(response)
        } catch (e: Exception) {
            _productsResponse.value = UiState.Error(ErrorType.EXCEPTION, e.message.toString())
        }
    }

    private fun handleResponse(response: Response<Product>): UiState<Product> {
        return when {
            !response.isSuccessful -> UiState.Error(ErrorType.API_ERROR)
            response.code() != 200 -> UiState.Error(ErrorType.API_ERROR)
            response.code() == 200 && (response.body() == null || response.body()
                ?.toString()?.contains("error", true) == true) ->
                UiState.Error(ErrorType.API_ERROR)

            response.code() == 200 && response.body()!= null -> {
                UiState.Success(response.body())
            }

            else -> UiState.Error(ErrorType.UNKNOWN)
        }
    }

    /** CACHE */
    val cart: LiveData<List<Product>> = readCartUseCase.execute().asLiveData()

    fun readProductCached(id: Int): LiveData<Product> {
        return readProductFromDBUseCase.execute(id).asLiveData()
    }

    fun addProductToCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            addProductToCartUseCase.execute(product.id)
        }
    }

    fun removeProductFromCart(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            removeProductFromCartUseCase.execute(productId)
        }
    }

}