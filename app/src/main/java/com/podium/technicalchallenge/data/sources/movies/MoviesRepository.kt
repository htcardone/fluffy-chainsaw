package com.podium.technicalchallenge.data.sources.movies

import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.data.entity.MovieFullEntity
import com.podium.technicalchallenge.data.network.queries.OrderBy
import com.podium.technicalchallenge.data.network.queries.Sort

interface MoviesRepository {

    suspend fun getTopMovies(limit: Int, forceRefresh: Boolean): Result<List<MovieEntity>>

    suspend fun getMoviesByGenre(genre: String, orderBy: OrderBy, sort: Sort,
                                 limit: Int): Result<List<MovieEntity>>

    suspend fun getMovieDetails(movieId: Int): Result<MovieFullEntity>
}