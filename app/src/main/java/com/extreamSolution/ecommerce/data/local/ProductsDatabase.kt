package com.extreamSolution.ecommerce.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1,
    exportSchema = false
)

abstract class ProductsDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}