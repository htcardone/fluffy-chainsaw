package com.podium.technicalchallenge.data.sources.movies.remote

import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.data.entity.MovieFullEntity
import com.podium.technicalchallenge.data.entity.MovieResponse
import com.podium.technicalchallenge.data.entity.MoviesResponse
import com.podium.technicalchallenge.data.network.queries.OrderBy
import com.podium.technicalchallenge.data.network.queries.Queries
import com.podium.technicalchallenge.data.network.queries.Sort
import com.podium.technicalchallenge.data.network.retrofit.GraphQLService
import com.podium.technicalchallenge.data.network.retrofit.GraphQlHelper
import com.podium.technicalchallenge.data.sources.movies.MoviesDataSource

class MoviesRemoteDataSource(
    private val service: GraphQLService
): MoviesDataSource {

    // TODO Add a trace to measure how long this request is taking
    override suspend fun getMovies(
        orderBy: OrderBy,
        sort: Sort,
        limit: Int
    ): Result<List<MovieEntity>> {

        val query = Queries.getMoviesQuery(orderBy, sort, limit)

        return try {
            val data = GraphQlHelper.post(query, service, MoviesResponse::class.java)

            if (data != null) {
                Result.Success(data.data.movies)
            } else {
                Result.Error(Exception())
            }
        } catch (e: Exception) {
            // TODO log error on Crashlytics
            Result.Error(e)
        }
    }

    override suspend fun getMoviesByGenre(
        genre: String,
        orderBy: OrderBy,
        sort: Sort,
        limit: Int
    ): Result<List<MovieEntity>> {

        val query = Queries.getMoviesByGenreQuery(genre, orderBy, sort, limit)

        return try {
            val data = GraphQlHelper.post(query, service, MoviesResponse::class.java)

            if (data != null) {
                Result.Success(data.data.movies)
            } else {
                Result.Error(Exception())
            }
        } catch (e: Exception) {
            // TODO log error on Crashlytics
            Result.Error(e)
        }
    }

    // TODO Add a trace to measure how long this request is taking
    override suspend fun getMovie(id: Int): Result<MovieFullEntity?> {
        val query = Queries.getMovieDetails(id)

        return try {
            val data = GraphQlHelper.post(query, service, MovieResponse::class.java)

            if (data != null) {
                Result.Success(data.data?.movie)
            } else {
                Result.Error(Exception())
            }
        } catch (e: Exception) {
            // TODO log error on Crashlytics
            Result.Error(e)
        }
    }
}