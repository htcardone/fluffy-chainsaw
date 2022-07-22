package com.podium.technicalchallenge.data.sources.genres.remote

import com.google.gson.Gson
import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.entity.GenresResponse
import com.podium.technicalchallenge.data.network.queries.Queries
import com.podium.technicalchallenge.data.network.retrofit.GraphQLService
import com.podium.technicalchallenge.data.sources.genres.GenresDataSource
import org.json.JSONObject

class GenresRemoteDataSource(
    private val service: GraphQLService
): GenresDataSource {

    // TODO Add a trace to measure how long this request is taking
    override suspend fun getAllGenres(): Result<List<String>> {

        val params = JSONObject().apply {
            put("query", Queries.getGenres())
        }

        val response = service.postGraphQL(params.toString())
        val jsonBody = response.body()

        return try {
            val data = Gson().fromJson(jsonBody, GenresResponse::class.java)

            if (data != null) {
                Result.Success(data.data.genres)
            } else {
                Result.Error(Exception())
            }
        } catch (e: Exception) {
            // TODO log error on Crashlytics
            Result.Error(e)
        }
    }
}