package com.podium.technicalchallenge.data.network.retrofit

import android.util.Log
import com.google.gson.Gson
import org.json.JSONObject

object GraphQlHelper {

    private const val LOG_TAG = "GraphQlHelper"
    private const val ENABLE_LOG = false

    suspend fun<T> post(query: String, service: GraphQLService, type: Class<T>): T? {
        val params = JSONObject().apply {
            put("query", query)
        }.toString()

        if (ENABLE_LOG) Log.d(LOG_TAG, "params=$params")

        val response = service.postGraphQL(params)
        val jsonBody = response.body()

        if (ENABLE_LOG) Log.d(LOG_TAG, "response=$response")

        return Gson().fromJson(jsonBody, type)
    }
}