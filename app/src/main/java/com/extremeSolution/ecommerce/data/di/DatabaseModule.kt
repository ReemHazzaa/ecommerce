package com.extremeSolution.ecommerce.data.di

import android.content.Context
import androidx.room.Room
import com.extremeSolution.ecommerce.app.Constants.DATABASE_NAME
import com.extremeSolution.ecommerce.data.local.ProductsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ProductsDatabase {
        return Room.databaseBuilder(
            context,
            ProductsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideProductsDao(database: ProductsDatabase) = database.productsDao()
}