package com.podium.technicalchallenge.data.sources.genres.remote

import android.util.Log
import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.entity.GenresResponse
import com.podium.technicalchallenge.data.network.queries.Queries
import com.podium.technicalchallenge.data.network.retrofit.GraphQLService
import com.podium.technicalchallenge.data.network.retrofit.GraphQlHelper
import com.podium.technicalchallenge.data.sources.genres.GenresDataSource

class GenresRemoteDataSource(
    private val service: GraphQLService
): GenresDataSource {

    // TODO Add a trace to measure how long this request is taking
    override suspend fun getAllGenres(): Result<List<String>> {

        return try {
            val data = GraphQlHelper.post(Queries.getGenres(), service, GenresResponse::class.java)

            if (data != null) {
                Result.Success(data.data.genres)
            } else {
                Result.Error(Exception())
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "getAllGenres", e)
            Result.Error(e)
        }
    }

    companion object {
        const val LOG_TAG = "GenresRemoteDataSource"
    }
}