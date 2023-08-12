package com.extremeSolution.ecommerce.domain.repo

import com.extremeSolution.ecommerce.domain.models.product.Product
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AppRepo {

    /** REMOTE */
    suspend fun getCategoriesList(): Response<List<String>>

    suspend fun getAllProducts(): Response<List<Product>>

    suspend fun getProductsInCategory(category: String): Response<List<Product>>

    suspend fun getProductDetails(productId: Int): Response<Product>

    /** LOCAL */
    suspend fun insertProductToDB(product: Product)

    fun readProductFromDB(productId: Int): Flow<Product>

    suspend fun deleteProductFromDB(product: Product)

    fun readAllProductsFromDB(): Flow<List<Product>>

    suspend fun deleteAllProductsFromDB()

    suspend fun addProductToCart(productId: Int)

    suspend fun removeProductFromCart(productId: Int)

    /** Datastore */
    suspend fun cacheCategories(categories: List<String>)

    fun readCategoriesFromCache(): Flow<List<String>>
}