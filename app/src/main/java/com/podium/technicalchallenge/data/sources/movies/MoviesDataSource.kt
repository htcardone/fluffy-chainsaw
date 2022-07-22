package com.podium.technicalchallenge.data.sources.movies

import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.data.entity.MovieFullEntity
import com.podium.technicalchallenge.data.network.queries.OrderBy
import com.podium.technicalchallenge.data.network.queries.Sort

interface MoviesDataSource {

    suspend fun getMovies(orderBy: OrderBy, sort: Sort, limit: Int): Result<List<MovieEntity>>

    suspend fun getMoviesByGenre(genre: String, orderBy: OrderBy, sort: Sort,
                                 limit: Int): Result<List<MovieEntity>>

    suspend fun getMovie(id: Int): Result<MovieFullEntity?>

}