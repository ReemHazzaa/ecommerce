package com.extreamSolution.ecommerce.data.di

import com.extreamSolution.ecommerce.data.remote.networkLayer.client.FakeStoreClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkClientsModule {

    @Provides
    @Singleton
    fun provideNetworkClient(retrofitBuilder: Retrofit.Builder): FakeStoreClient {
        return FakeStoreClient(retrofitBuilder)
    }

}