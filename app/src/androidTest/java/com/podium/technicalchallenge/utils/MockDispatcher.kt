package com.podium.technicalchallenge.utils

import android.util.Log
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

class MockDispatcher: Dispatcher() {

    private val getMoviesResponse = getJsonContent("GetMoviesResponse.json")
    private val getMoviesByGenreResponse = getJsonContent("getMoviesByGenreResponse.json")
    private val getGenresResponse = getJsonContent("GetGenresResponse.json")
    private val getMovieResponse = getJsonContent("GetMovieResponse.json")

    private fun getJsonContent(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-responses/$fileName")

        val source = inputStream?.let { inputStream.source().buffer() }
        return source?.readString(StandardCharsets.UTF_8) ?: ""
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        val body = request.body.readUtf8()
        Log.d("MockWebServer", "${request.method}: ${request.path}")
        Log.d("MockWebServer", body)

        return when {
            body.contains("GetMoviesQuery") ->
                MockResponse().setResponseCode(200).setBody(getMoviesResponse)

            body.contains("GetMoviesByGenre") ->
                MockResponse().setResponseCode(200).setBody(getMoviesByGenreResponse)

            body.contains("GetGenresQuery") ->
                MockResponse().setResponseCode(200).setBody(getGenresResponse)

            body.contains("GetMovieQuery") ->
                MockResponse().setResponseCode(200).setBody(getMovieResponse)

            else ->
                MockResponse().setResponseCode(404)
        }
    }
}