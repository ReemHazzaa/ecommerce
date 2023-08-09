package com.extremeSolution.ecommerce.data.remote.networkLayer.client


import com.extremeSolution.ecommerce.BuildConfig
import retrofit2.Retrofit

class FakeStoreClient(retrofitBuilder: Retrofit.Builder) : NetworkClient(retrofitBuilder) {

    override val baseUrl = BuildConfig.FAKE_STORE_API_BASE_URL
}