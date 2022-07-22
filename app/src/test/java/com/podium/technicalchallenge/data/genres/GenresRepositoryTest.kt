package com.podium.technicalchallenge.data.genres

import com.google.common.truth.Truth.assertThat
import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.sources.genres.DefaultGenresRepository
import com.podium.technicalchallenge.data.sources.genres.GenresRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GenresRepositoryTest {

    private lateinit var remoteDataSource: GenresFakeDataSource
    private lateinit var repository: GenresRepository

    @Before
    fun setup() {
        remoteDataSource = GenresFakeDataSource()
        repository = DefaultGenresRepository(remoteDataSource)
    }

    @Test
    fun `get all genres without cache, should get from remote`() = runTest {
        // Given
        val expectedGenres = listOf("Genre A", "Genre B")
        remoteDataSource = GenresFakeDataSource(expectedGenres.toMutableList())
        repository = DefaultGenresRepository(remoteDataSource)

        // When
        val result = repository.getAllGenres(false)

        // Then
        val genres = (result as Result.Success).data
        assertThat(genres).isEqualTo(expectedGenres)
    }

    @Test
    fun `get all genres without forcing refresh, should return from cache`() = runTest {
        // Given
        val firstTimeGenres = listOf("Genre A", "Genre B")
        remoteDataSource = GenresFakeDataSource(firstTimeGenres.toMutableList())
        repository = DefaultGenresRepository(remoteDataSource)
        repository.getAllGenres(true)

        // When
        val afterRefreshGenres = mutableListOf("Genre A", "Genre B", "Genre C")
        remoteDataSource.replaceGenres(afterRefreshGenres)
        val result = repository.getAllGenres(false)

        // Then
        val genres = (result as Result.Success).data
        assertThat(genres).isEqualTo(firstTimeGenres)
    }

    @Test
    fun `get all genres forcing refresh, should return from remote`() = runTest {
        // Given
        val firstTimeGenres = listOf("Genre A", "Genre B")
        remoteDataSource = GenresFakeDataSource(firstTimeGenres.toMutableList())
        repository = DefaultGenresRepository(remoteDataSource)
        repository.getAllGenres(true)

        // When
        val afterRefreshGenres = listOf("Genre A", "Genre B", "Genre C")
        remoteDataSource.replaceGenres(afterRefreshGenres)
        val result = repository.getAllGenres(true)

        // Then
        val genres = (result as Result.Success).data
        assertThat(genres).isEqualTo(afterRefreshGenres)
    }

    @Test
    fun `get all genres forcing refresh, should keep after error`() = runTest {
        // Given
        val firstTimeGenres = listOf("Genre A", "Genre B")
        remoteDataSource = GenresFakeDataSource(firstTimeGenres.toMutableList())
        repository = DefaultGenresRepository(remoteDataSource)
        repository.getAllGenres(true)

        // When
        remoteDataSource.isOffline = true
        val errorResult = repository.getAllGenres(true)
        remoteDataSource.isOffline = false
        val cachedResult = repository.getAllGenres(false)

        // Then
        val genres = (cachedResult as Result.Success).data
        assertThat(genres).isEqualTo(firstTimeGenres)
    }
}