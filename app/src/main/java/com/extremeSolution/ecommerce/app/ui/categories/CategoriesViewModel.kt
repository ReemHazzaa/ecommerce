package com.extremeSolution.ecommerce.app.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.extremeSolution.ecommerce.app.uiState.ErrorType
import com.extremeSolution.ecommerce.app.uiState.UiState
import com.extremeSolution.ecommerce.app.utils.Constants.VIEW_TYPE_JEWELRY_TITLE
import com.extremeSolution.ecommerce.app.utils.Constants.VIEW_TYPE_MEN_TITLE
import com.extremeSolution.ecommerce.app.utils.Constants.VIEW_TYPE_WOMEN_TITLE
import com.extremeSolution.ecommerce.data.remote.networkLayer.NetworkManager
import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.usecases.categories.GetCategoriesUseCase
import com.extremeSolution.ecommerce.domain.usecases.productsInCategory.GetProductsInCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesUseCase: GetCategoriesUseCase,
    private val productsInCategoryUseCase: GetProductsInCategoryUseCase,
    private val networkManager: NetworkManager
) : ViewModel() {
    private var _categoriesResponse: MutableLiveData<UiState<List<String>>> = MutableLiveData()
    val categoriesResponse: LiveData<UiState<List<String>>> = _categoriesResponse

    private var _productsMenResponse: MutableLiveData<UiState<List<Product>>> = MutableLiveData()
    val productsMenResponse: LiveData<UiState<List<Product>>> = _productsMenResponse

    private var _productsWomenResponse: MutableLiveData<UiState<List<Product>>> = MutableLiveData()
    val productsWomenResponse: LiveData<UiState<List<Product>>> = _productsWomenResponse

    private var _productsJewelResponse: MutableLiveData<UiState<List<Product>>> = MutableLiveData()
    val productsJewelResponse: LiveData<UiState<List<Product>>> = _productsJewelResponse

    private var _productsElectResponse: MutableLiveData<UiState<List<Product>>> = MutableLiveData()
    val productsElectResponse: LiveData<UiState<List<Product>>> = _productsElectResponse

    fun safeGetCategoriesCall() {
        if (networkManager.isNetworkAvailable()) {
            getCategories()
        } else {
            _categoriesResponse.value = UiState.Error(ErrorType.API_ERROR)
            _productsMenResponse.value = UiState.Error(ErrorType.API_ERROR)
            _productsWomenResponse.value = UiState.Error(ErrorType.API_ERROR)
            _productsJewelResponse.value = UiState.Error(ErrorType.API_ERROR)
            _productsElectResponse.value = UiState.Error(ErrorType.API_ERROR)
        }
    }

    private fun getCategories() = viewModelScope.launch {
        _categoriesResponse.value = UiState.Loading()
        try {
            val response = categoriesUseCase.execute()
            _categoriesResponse.value = handleCategoriesResponse(response)
        } catch (e: Exception) {
            _categoriesResponse.value = UiState.Error(ErrorType.EXCEPTION, e.message.toString())
        }
    }

    private fun handleCategoriesResponse(categories: Response<List<String>>): UiState<List<String>> {
        return when {
            !categories.isSuccessful -> UiState.Error(ErrorType.API_ERROR)
            categories.code() != 200 -> UiState.Error(ErrorType.API_ERROR)
            categories.code() == 200 && (categories.body().isNullOrEmpty() || categories.body()
                ?.toString()?.contains("error", true) == true) ->
                UiState.Error(ErrorType.API_ERROR)

            categories.code() == 200 && categories.body()?.isNotEmpty() == true -> {
                getProductsInEveryCategoryAsync(categories.body()!!)
                UiState.Success(categories.body())
            }

            else -> UiState.Error(ErrorType.UNKNOWN)
        }
    }

    private fun getProductsInEveryCategoryAsync(categories: List<String>) {

        _productsMenResponse.value = UiState.Loading()
        _productsWomenResponse.value = UiState.Loading()
        _productsJewelResponse.value = UiState.Loading()
        _productsElectResponse.value = UiState.Loading()

        viewModelScope.launch {
            supervisorScope {
                categories.forEach { category ->
                    when (category) {
                        VIEW_TYPE_MEN_TITLE -> {
                            val productsMenDeferred = viewModelScope.async {
                                productsInCategoryUseCase.execute(
                                    GetProductsInCategoryUseCase.Params(category)
                                )
                            }

                            val productsMen: Response<List<Product>>? = try {
                                productsMenDeferred.await()
                            } catch (e: Exception) {
                                _productsMenResponse.value =
                                    UiState.Error(ErrorType.EXCEPTION, e.message.toString())
                                null
                            }
                            _productsMenResponse.value =
                                productsMen?.let { handleProductsInCategoryResponse(it) }

                        }

                        VIEW_TYPE_WOMEN_TITLE -> {
                            val productsWomenDeferred = viewModelScope.async {
                                productsInCategoryUseCase.execute(
                                    GetProductsInCategoryUseCase.Params(category)
                                )
                            }
                            val productsWomen: Response<List<Product>>? = try {
                                productsWomenDeferred.await()
                            } catch (e: Exception) {
                                _productsWomenResponse.value =
                                    UiState.Error(ErrorType.EXCEPTION, e.message.toString())
                                null
                            }
                            _productsWomenResponse.value =
                                productsWomen?.let { handleProductsInCategoryResponse(it) }
                        }

                        VIEW_TYPE_JEWELRY_TITLE -> {
                            val productsJewelDeferred = viewModelScope.async {
                                productsInCategoryUseCase.execute(
                                    GetProductsInCategoryUseCase.Params(category)
                                )
                            }
                            val productsJewelry: Response<List<Product>>? = try {
                                productsJewelDeferred.await()
                            } catch (e: Exception) {
                                _productsJewelResponse.value =
                                    UiState.Error(ErrorType.EXCEPTION, e.message.toString())
                                null
                            }
                            _productsJewelResponse.value =
                                productsJewelry?.let { handleProductsInCategoryResponse(it) }
                        }

                        else -> {
                            val productsElectDeferred = viewModelScope.async {
                                productsInCategoryUseCase.execute(
                                    GetProductsInCategoryUseCase.Params(category)
                                )
                            }
                            val productsElectronics: Response<List<Product>>? = try {
                                productsElectDeferred.await()
                            } catch (e: Exception) {
                                _productsElectResponse.value =
                                    UiState.Error(ErrorType.EXCEPTION, e.message.toString())
                                null
                            }
                            _productsElectResponse.value =
                                productsElectronics?.let { handleProductsInCategoryResponse(it) }
                        }
                    }
                }
            }
        }
    }

    private fun handleProductsInCategoryResponse(products: Response<List<Product>>?): UiState<List<Product>> {
        return when {
            products == null -> UiState.Error(ErrorType.API_ERROR)
            !products.isSuccessful -> UiState.Error(ErrorType.API_ERROR)
            products.code() != 200 -> UiState.Error(ErrorType.API_ERROR)
            products.code() == 200 && (products.body().isNullOrEmpty()) ->
                UiState.Error(ErrorType.API_ERROR)

            products.code() == 200 && products.body()?.isNotEmpty() == true ->
                UiState.Success(products.body())

            else -> UiState.Error(ErrorType.UNKNOWN)
        }
    }

}