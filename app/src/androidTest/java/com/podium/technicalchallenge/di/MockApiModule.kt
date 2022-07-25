package com.podium.technicalchallenge.di

import com.podium.technicalchallenge.data.network.retrofit.GraphQLService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApiModule::class]
)
object MockApiModule {
    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8080")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideGraphQLService(retrofit: Retrofit): GraphQLService {
        return retrofit.create(GraphQLService::class.java)
    }
}