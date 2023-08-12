package com.extremeSolution.ecommerce.data.repo

import com.extremeSolution.ecommerce.data.datastore.AppDataStore
import com.extremeSolution.ecommerce.data.local.ProductsDao
import com.extremeSolution.ecommerce.data.remote.apiService.FakeStoreApiService
import com.extremeSolution.ecommerce.domain.models.product.Product
import com.extremeSolution.ecommerce.domain.repo.AppRepo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class AppRepoImpl @Inject constructor(
    private val apiService: FakeStoreApiService,
    private val productsDao: ProductsDao,
    private val appDataStore: AppDataStore
) : AppRepo {

    /** REMOTE */
    override suspend fun getCategoriesList(): Response<List<String>> {
        return apiService.getCategoriesList()
    }

    override suspend fun getAllProducts(): Response<List<Product>> {
        return apiService.getAllProducts()
    }

    override suspend fun getProductsInCategory(category: String): Response<List<Product>> {
        return apiService.getProductsInCategory(category)
    }

    override suspend fun getProductDetails(productId: Int): Response<Product> {
        return apiService.getProductDetails(productId)
    }

    /** LOCAL */
    override suspend fun insertProductToDB(product: Product) {
        return productsDao.insertProduct(product)
    }

    override suspend fun readProductFromDB(productId: Int): Flow<Product> {
        return productsDao.readProduct(productId)
    }

    override suspend fun deleteProductFromDB(product: Product) {
        return productsDao.deleteProduct(product)
    }

    override fun readAllProductsFromDB(): Flow<List<Product>> {
        return productsDao.readAllProducts()
    }

    override suspend fun deleteAllProductsFromDB() {
        return productsDao.deleteAllProducts()
    }

    override suspend fun addProductToCart(productId: Int) {
        return productsDao.addProductToCart(productId)
    }

    override suspend fun removeProductFromCart(productId: Int) {
        return productsDao.removeProductFromCart(productId)
    }

    /** DATASTORE */
    override suspend fun cacheCategories(categories: List<String>) {
        return appDataStore.saveCategories(categories)
    }

    override fun readCategoriesFromCache(): Flow<List<String>> {
        return appDataStore.readCategories
    }

}