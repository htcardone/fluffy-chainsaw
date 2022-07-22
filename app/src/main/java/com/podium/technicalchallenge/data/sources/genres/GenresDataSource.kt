package com.podium.technicalchallenge.data.sources.genres

import com.podium.technicalchallenge.data.Result

interface GenresDataSource {

    suspend fun getAllGenres(): Result<List<String>>
}