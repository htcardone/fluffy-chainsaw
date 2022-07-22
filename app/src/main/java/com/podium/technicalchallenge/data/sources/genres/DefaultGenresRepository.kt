package com.podium.technicalchallenge.data.sources.genres

import com.podium.technicalchallenge.data.Result

class DefaultGenresRepository(
    private val remoteDataSource: GenresDataSource
): GenresRepository {

    private val cachedGenres = mutableListOf<String>()

    override suspend fun getAllGenres(forceRefresh: Boolean): Result<List<String>> {
        if (forceRefresh || cachedGenres.isEmpty()) {
            val result = remoteDataSource.getAllGenres()

            when (result) {
                is Result.Success -> {
                    cachedGenres.clear()
                    cachedGenres.addAll(result.data)
                    return result
                }
                is Result.Error -> {
                    return result
                }
            }
        } else {
            return Result.Success(cachedGenres)
        }
    }
}