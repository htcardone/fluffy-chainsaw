package com.podium.technicalchallenge.data.network.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GraphQLService {
    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun postGraphQL(@Body body: String): Response<String>
}
