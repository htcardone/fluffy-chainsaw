package com.podium.technicalchallenge.data.sources.movies

import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.data.entity.MovieFullEntity
import com.podium.technicalchallenge.data.network.queries.OrderBy
import com.podium.technicalchallenge.data.network.queries.Sort

class MoviesFakeDataSource(
    private val movies: MutableMap<Int, MovieEntity> = mutableMapOf(),
    private val fullMovies: MutableMap<Int, MovieFullEntity> = mutableMapOf(),
    var isOffline: Boolean = false
): MoviesDataSource {

    override suspend fun getMovies(
        orderBy: OrderBy,
        sort: Sort,
        limit: Int
    ): Result<List<MovieEntity>> {
        return if (isOffline) Result.Error(Exception())
        else Result.Success(movies.values.sortedBy { it.voteAverage }.take(limit))
    }

    override suspend fun getMoviesByGenre(
        genre: String,
        orderBy: OrderBy,
        sort: Sort,
        limit: Int
    ): Result<List<MovieEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovie(id: Int): Result<MovieFullEntity?> {
        if (isOffline) return Result.Error(Exception())
        return Result.Success(fullMovies[id])
    }

    fun replaceMovies(movies: MutableMap<Int, MovieEntity>) {
        this.movies.clear()
        this.movies.putAll(movies)
    }

    fun replaceFullMovies(movies: MutableMap<Int, MovieFullEntity>) {
        this.fullMovies.clear()
        this.fullMovies.putAll(movies)
    }
}