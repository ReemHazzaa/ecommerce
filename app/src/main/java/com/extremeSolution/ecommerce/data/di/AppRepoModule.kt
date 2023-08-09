package com.extremeSolution.ecommerce.data.di


import com.extremeSolution.ecommerce.data.repo.AppRepoImpl
import com.extremeSolution.ecommerce.domain.repo.AppRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppRepoModule {
    @Binds
    abstract fun provideAppRepoModule(appRepoImpl: AppRepoImpl): AppRepo
}