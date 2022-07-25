package com.podium.technicalchallenge.data.sources.movies

import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.data.entity.MovieFullEntity

interface MoviesRepository {

    suspend fun getTopMovies(limit: Int, forceRefresh: Boolean): Result<List<MovieEntity>>

    suspend fun getMovies(genre: String?, orderBy: String, sort: String,
                                 limit: Int): Result<List<MovieEntity>>

    suspend fun getMovieDetails(movieId: Int): Result<MovieFullEntity>
}