package com.growd25.developerslife.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.growd25.developerslife.data.DevLifeApi
import com.growd25.developerslife.repository.DefaultDevLifeRepository
import com.growd25.developerslife.repository.DevlLifeRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class MainModule {

    @Singleton
    @Provides
    fun provideRepository(devLifeApi: DevLifeApi): DevlLifeRepository =
        DefaultDevLifeRepository(devLifeApi)

    @Provides
    fun provideRetrofitService(retrofit: Retrofit): DevLifeApi =
        retrofit.create(DevLifeApi::class.java)

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(DevLifeApi.BASE_URL)
        .build()
}
