package com.podium.technicalchallenge.di

import com.podium.technicalchallenge.data.network.retrofit.GraphQLService
import com.podium.technicalchallenge.data.sources.genres.DefaultGenresRepository
import com.podium.technicalchallenge.data.sources.genres.GenresDataSource
import com.podium.technicalchallenge.data.sources.genres.GenresRepository
import com.podium.technicalchallenge.data.sources.genres.remote.GenresRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GenresModule {

    @Qualifier
    annotation class RemoteGenresDataSource

    @Qualifier
    annotation class LocalGenresDataSource

    @Singleton
    @RemoteGenresDataSource
    @Provides
    fun provideGenresRemoteDataSource(service: GraphQLService): GenresDataSource {
        return GenresRemoteDataSource(service)
    }

    @Singleton
    @Provides
    fun provideGenresRepository(
        @RemoteGenresDataSource remoteDataSource: GenresDataSource
    ): GenresRepository {
        return DefaultGenresRepository(remoteDataSource)
    }
}