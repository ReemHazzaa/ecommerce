package com.extremeSolution.ecommerce.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.extremeSolution.ecommerce.app.uiState.ErrorType
import com.extremeSolution.ecommerce.app.uiState.UiState
import com.extremeSolution.ecommerce.data.remote.networkLayer.NetworkManager
import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.usecases.datastore.cacheCategories.CacheCategoriesUseCase
import com.extremeSolution.ecommerce.domain.usecases.datastore.readCategories.ReadCachedCategoriesUseCase
import com.extremeSolution.ecommerce.domain.usecases.local.cacheProduct.CacheProductUseCase
import com.extremeSolution.ecommerce.domain.usecases.local.readCachedProducts.ReadCachedProductsUseCase
import com.extremeSolution.ecommerce.domain.usecases.remote.categories.GetCategoriesUseCase
import com.extremeSolution.ecommerce.domain.usecases.remote.products.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import retrofit2.Response
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoriesUseCase: GetCategoriesUseCase,
    private val productsUseCase: GetProductsUseCase,
    private val cacheProductUseCase: CacheProductUseCase,
    readCachedProductsUseCase: ReadCachedProductsUseCase,
    private val cacheCategoriesUseCase: CacheCategoriesUseCase,
    readCachedCategoriesUseCase: ReadCachedCategoriesUseCase,
    private val networkManager: NetworkManager
) : ViewModel() {

    private var _categoriesResponse: MutableLiveData<UiState<List<String>>> = MutableLiveData()
    val categoriesResponse: LiveData<UiState<List<String>>> = _categoriesResponse

    private var _productsResponse: MutableLiveData<UiState<List<Product>>> = MutableLiveData()
    val productsResponse: LiveData<UiState<List<Product>>> = _productsResponse

    fun getCategoriesAndProductsSafeCall() {
        if (networkManager.isNetworkAvailable()) {
            getCategoriesAndProductsAsync()
        } else {
            _categoriesResponse.value = UiState.Error(ErrorType.API_ERROR)
            _productsResponse.value = UiState.Error(ErrorType.API_ERROR)
        }
    }

    private fun getCategoriesAndProductsAsync() {
        _categoriesResponse.value = UiState.Loading()
        _productsResponse.value = UiState.Loading()

        viewModelScope.launch {
            supervisorScope {
                val categoriesDeferred = viewModelScope.async { categoriesUseCase.execute() }
                val productsDeferred = viewModelScope.async { productsUseCase.execute() }

                val categories: Response<List<String>>? = try {
                    categoriesDeferred.await()
                } catch (e: Exception) {
                    _categoriesResponse.value =
                        UiState.Error(ErrorType.EXCEPTION, e.message.toString())
                    null
                }
                _categoriesResponse.value = categories?.let { handleCategoriesResponse(it) }


                val products: Response<List<Product>>? = try {
                    productsDeferred.await()
                } catch (e: Exception) {
                    _productsResponse.value =
                        UiState.Error(ErrorType.EXCEPTION, e.message.toString())
                    null
                }
                _productsResponse.value = products?.let { handleProductsResponse(it) }
            }
        }

    }

    private fun handleProductsResponse(products: Response<List<Product>>): UiState<List<Product>> {
        return when {
            !products.isSuccessful -> UiState.Error(ErrorType.API_ERROR)
            products.code() != 200 -> UiState.Error(ErrorType.API_ERROR)
            products.code() == 200 && (products.body().isNullOrEmpty()) ->
                UiState.Error(ErrorType.API_ERROR)

            products.code() == 200 && products.body()?.isNotEmpty() == true -> {
                cacheProducts(products.body()!!)
                UiState.Success(products.body())
            }

            else -> UiState.Error(ErrorType.UNKNOWN)
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
                cacheCategories(categories.body()!!)
                UiState.Success(categories.body())
            }

            else -> UiState.Error(ErrorType.UNKNOWN)
        }
    }

    /** CACHE */

    val productsCache: LiveData<List<Product>> = readCachedProductsUseCase.execute().asLiveData()
    val categoriesCache: LiveData<List<String>> = readCachedCategoriesUseCase.execute().asLiveData()

    private fun cacheProducts(productList: List<Product>) {
        viewModelScope.launch(Dispatchers.IO) {
            productList.forEach { item ->
                cacheProductUseCase.execute(item)
            }
        }
    }

    private fun cacheCategories(categories: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            cacheCategoriesUseCase.execute(categories)
        }
    }

    /** SEARCH */

    fun searchProducts(sQuery: String, globalProductsList: List<Product>): List<Product> {
        val searchList = mutableListOf<Product>()

        // running a for loop to compare elements.
        for (item in globalProductsList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.category.lowercase(Locale.getDefault())
                    .contains(sQuery.lowercase(Locale.getDefault()))
                || item.title.lowercase(Locale.getDefault())
                    .contains(sQuery.lowercase(Locale.getDefault()))
                || item.description.lowercase(Locale.getDefault())
                    .contains(sQuery.lowercase(Locale.getDefault()))
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                searchList.add(item)
            }
        }
        return if (sQuery == "") {
            globalProductsList
        } else {
            searchList
        }
    }

}