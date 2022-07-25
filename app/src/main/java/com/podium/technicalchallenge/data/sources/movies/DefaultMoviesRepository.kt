package com.podium.technicalchallenge.data.sources.movies

import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.data.entity.MovieFullEntity
import com.podium.technicalchallenge.data.entity.MovieNotFoundException
import com.podium.technicalchallenge.data.network.queries.OrderBy
import com.podium.technicalchallenge.data.network.queries.Sort

class DefaultMoviesRepository(
    private val remoteDataSource: MoviesDataSource
): MoviesRepository {

    private val topMoviesCache: MutableList<MovieEntity> = mutableListOf()
    private val moviesCache: MutableMap<Int, MovieFullEntity> = mutableMapOf()

    override suspend fun getTopMovies(limit: Int, forceRefresh: Boolean): Result<List<MovieEntity>> {

        if (forceRefresh || topMoviesCache.isEmpty()) {
            val result = remoteDataSource.getMovies(OrderBy.VOTE_AVERAGE, Sort.DESC, limit)

            when (result) {
                is Result.Success -> {
                    val movies = result.data
                    topMoviesCache.clear()
                    topMoviesCache.addAll(movies)
                    return result
                }

                is Result.Error -> {
                    return result
                }
            }
        }

        else return Result.Success(topMoviesCache)
    }

    override suspend fun getMovies(
        genre: String?,
        orderBy: String,
        sort: String,
        limit: Int
    ): Result<List<MovieEntity>> {
        return if (genre == null) remoteDataSource.getMovies(orderBy, sort, limit)
        else remoteDataSource.getMoviesByGenre(genre, orderBy, sort, limit)
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieFullEntity> {
        moviesCache[movieId]?.let { movie ->
            return Result.Success(movie)
        }

        val result = remoteDataSource.getMovie(movieId)

        when (result) {
            is Result.Success -> {
                result.data?.let { movie ->
                    moviesCache[movie.id] = movie
                    return Result.Success(movie)
                } ?: run {
                    return Result.Error(MovieNotFoundException())
                }
            }

            is Result.Error -> {
                return result
            }
        }
    }
}