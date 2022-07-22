package com.podium.technicalchallenge.data.sources.genres

import com.podium.technicalchallenge.data.Result

class GenresFakeDataSource(
    private val genres: MutableList<String> = mutableListOf(),
    var isOffline: Boolean = false
): GenresDataSource {

    override suspend fun getAllGenres(): Result<List<String>> {
        return if (isOffline) Result.Error(Exception())
        else Result.Success(genres)
    }

    fun replaceGenres(genres: List<String>) {
        this.genres.clear()
        this.genres.addAll(genres)
    }
}