package com.podium.technicalchallenge.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


class ApiClient {
    val API_URL = "https://podium-fe-challenge-2021.netlify.app/.netlify/functions/"

    companion object {
        private var INSTANCE: ApiClient? = null
        fun getInstance() = INSTANCE
            ?: ApiClient().also {
                INSTANCE = it
            }
    }

    fun provideRetrofitClient(): Retrofit {
        return Retrofit
            .Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
}
