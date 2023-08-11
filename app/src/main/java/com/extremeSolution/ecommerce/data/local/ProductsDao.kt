package com.extremeSolution.ecommerce.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.extremeSolution.ecommerce.domain.models.product.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM products_table WHERE id = :productId")
    fun readProduct(productId: Int): Flow<Product>

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM products_table")
    fun readAllProducts(): Flow<List<Product>>

    @Query("DELETE FROM products_table")
    suspend fun deleteAllProducts()

    @Query("UPDATE products_table SET inCart = 1 WHERE id = :productId")
    fun addProductToCart(productId: Int)

    @Query("UPDATE products_table SET inCart = 0 WHERE id = :productId")
    fun removeProductFromCart(productId: Int)
}