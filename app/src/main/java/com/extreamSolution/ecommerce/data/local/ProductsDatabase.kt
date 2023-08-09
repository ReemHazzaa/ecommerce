package com.extreamSolution.ecommerce.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.extreamSolution.ecommerce.domain.models.product.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ProductsTypeConverter::class)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}