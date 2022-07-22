package com.podium.technicalchallenge.di

import com.podium.technicalchallenge.data.network.retrofit.GraphQLService
import com.podium.technicalchallenge.data.sources.movies.DefaultMoviesRepository
import com.podium.technicalchallenge.data.sources.movies.MoviesDataSource
import com.podium.technicalchallenge.data.sources.movies.MoviesRepository
import com.podium.technicalchallenge.data.sources.movies.remote.MoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {

    @Qualifier
    annotation class RemoteMoviesDataSource

    @Qualifier
    annotation class LocalMoviesDataSource

    @Singleton
    @RemoteMoviesDataSource
    @Provides
    fun provideMoviesRemoteDataSource(service: GraphQLService): MoviesDataSource {
        return MoviesRemoteDataSource(service)
    }

    @Singleton
    @Provides
    fun provideMoviesRepository(
        @RemoteMoviesDataSource remoteDataSource: MoviesDataSource
    ): MoviesRepository {
        return DefaultMoviesRepository(remoteDataSource)
    }
}