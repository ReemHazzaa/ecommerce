package com.extreamSolution.ecommerce.data.di

import com.extreamSolution.ecommerce.data.remote.apiService.FakeStoreApiService
import com.extreamSolution.ecommerce.data.remote.networkLayer.client.FakeStoreClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServicesModule {

    @Provides
    @Singleton
    fun provideApiService(client: FakeStoreClient): FakeStoreApiService {
        return client.build().create(FakeStoreApiService::class.java)
    }

}