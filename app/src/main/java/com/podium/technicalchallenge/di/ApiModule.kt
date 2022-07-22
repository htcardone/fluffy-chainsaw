package com.podium.technicalchallenge.di

import com.podium.technicalchallenge.BuildConfig
import com.podium.technicalchallenge.data.network.retrofit.GraphQLService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        return Retrofit
            .Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(BuildConfig.API_HOST)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideGraphQLService(retrofit: Retrofit): GraphQLService {
        return retrofit.create(GraphQLService::class.java)
    }
}