package com.podium.technicalchallenge.data.sources.movies

import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.data.entity.MovieFullEntity

interface MoviesDataSource {

    suspend fun getMovies(orderBy: String, sort: String, limit: Int): Result<List<MovieEntity>>

    suspend fun getMoviesByGenre(genre: String, orderBy: String, sort: String,
                                 limit: Int): Result<List<MovieEntity>>

    suspend fun getMovie(id: Int): Result<MovieFullEntity?>

}