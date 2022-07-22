package com.podium.technicalchallenge.data.sources.genres

import com.podium.technicalchallenge.data.Result

interface GenresRepository {

    suspend fun getAllGenres(forceRefresh: Boolean): Result<List<String>>
}