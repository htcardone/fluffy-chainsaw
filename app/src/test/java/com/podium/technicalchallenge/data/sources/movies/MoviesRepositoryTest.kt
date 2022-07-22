package com.podium.technicalchallenge.data.sources.movies

import com.google.common.truth.Truth.assertThat
import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.entity.DirectorEntity
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.data.entity.MovieFullEntity
import com.podium.technicalchallenge.data.entity.MovieNotFoundException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesRepositoryTest {

    private lateinit var remoteDataSource: MoviesFakeDataSource
    private lateinit var repository: MoviesRepository

    private val moviesFakes = listOf(
        MovieEntity(0, "Movie 0", "", 5f, null),
        MovieEntity(1, "Movie 1", "", 6f, null),
        MovieEntity(2, "Movie 2", "", 7f, null),
        MovieEntity(3, "Movie 3", "", 8f, null),
        MovieEntity(4, "Movie 4", "", 9f, null)
    )

    private val fullMoviesFakes = listOf(
        MovieFullEntity(0, "Movie 0", "", 1f, "", null, DirectorEntity(0, "Director"), emptyList()),
        MovieFullEntity(1, "Movie 1", "", 1f, "", null, DirectorEntity(0, "Director"), emptyList())
    )

    @Test
    fun `get top movies without cache, should get from remote`() = runTest {
        // Given
        val expectedMovies = moviesFakes
        remoteDataSource = MoviesFakeDataSource(moviesFakes.associateBy { it.id }.toMutableMap())
        repository = DefaultMoviesRepository(remoteDataSource)

        // When
        val result = repository.getTopMovies(5, false)

        // Then
        val movies = (result as Result.Success).data
        assertThat(movies).isEqualTo(expectedMovies)
    }

    @Test
    fun `get top movies without forcing refresh, should return from cache`() = runTest {
        // Given
        val firstTimeMovies = moviesFakes.take(2).associateBy { it.id }.toMutableMap()
        remoteDataSource = MoviesFakeDataSource(firstTimeMovies)
        repository = DefaultMoviesRepository(remoteDataSource)
        repository.getTopMovies(5, false)

        // When
        remoteDataSource.isOffline = true
        val result = repository.getTopMovies(5, false)

        // Then
        val movies = (result as Result.Success).data
        val expectedMovies = moviesFakes.take(2)
        assertThat(movies).isEqualTo(expectedMovies)
    }

    @Test
    fun `get top movies forcing refresh, should return from remote`() = runTest {
        // Given
        val firstTimeMovies = moviesFakes.take(2).associateBy { it.id }.toMutableMap()
        remoteDataSource = MoviesFakeDataSource(firstTimeMovies)
        repository = DefaultMoviesRepository(remoteDataSource)
        repository.getTopMovies(5, false)

        // When
        val afterRefreshMovies = moviesFakes.take(3).associateBy { it.id }.toMutableMap()
        remoteDataSource.replaceMovies(afterRefreshMovies)
        val result = repository.getTopMovies(5, true)

        // Then
        val movies = (result as Result.Success).data
        val expectedMovies = moviesFakes.take(3)
        assertThat(movies).isEqualTo(expectedMovies)
    }

    @Test
    fun `get movie details with wrong id, should return MovieNotFound exception`() = runTest {
        // Given
        remoteDataSource = MoviesFakeDataSource(
            fullMovies = fullMoviesFakes.associateBy { it.id }.toMutableMap()
        )
        repository = DefaultMoviesRepository(remoteDataSource)
        val movieId = -1

        // When
        val result = repository.getMovieDetails(movieId)

        // Then
        val error = (result as Result.Error)
        assertThat(error.exception).isInstanceOf(MovieNotFoundException::class.java)
    }

    @Test
    fun `get movie details, should return from remote`() = runTest {
        // Given
        remoteDataSource = MoviesFakeDataSource(
            fullMovies = fullMoviesFakes.associateBy { it.id }.toMutableMap()
        )
        repository = DefaultMoviesRepository(remoteDataSource)
        val movieId = 0

        // When
        val result = repository.getMovieDetails(movieId)

        // Then
        val movie = (result as Result.Success).data
        assertThat(movie).isEqualTo(fullMoviesFakes[0])
    }

    @Test
    fun `get movie details for second time, should return from cache`() = runTest {
        // Given
        remoteDataSource = MoviesFakeDataSource(
            fullMovies = fullMoviesFakes.associateBy { it.id }.toMutableMap()
        )
        repository = DefaultMoviesRepository(remoteDataSource)
        repository.getMovieDetails(0) // populates cache
        remoteDataSource.isOffline = true // turn remote off

        // When
        val result = repository.getMovieDetails(0)

        // Then
        val movie = (result as Result.Success).data
        assertThat(movie).isEqualTo(fullMoviesFakes[0])
    }
}