package com.podium.technicalchallenge.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.podium.technicalchallenge.MainDispatcherRule
import com.podium.technicalchallenge.data.entity.CastEntity
import com.podium.technicalchallenge.data.entity.DirectorEntity
import com.podium.technicalchallenge.data.entity.MovieFullEntity
import com.podium.technicalchallenge.data.sources.movies.DefaultMoviesRepository
import com.podium.technicalchallenge.data.sources.movies.MoviesFakeDataSource
import com.podium.technicalchallenge.data.sources.movies.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var moviesDataSource: MoviesFakeDataSource
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var viewModel: MovieViewModel

    private val fakeMovie = MovieFullEntity(
        0,
        "Title",
        "2020-04-12",
        8.4f,
        1,
        80,
        "Overview.",
        null,
        listOf("Fantasy", "Action", "Adventure", "Animation"),
        DirectorEntity(0, "Director Name"),
        listOf(
            CastEntity("Person Name", null),
            CastEntity("Person Name", null)
        )
    )

    @Before
    fun setup() {
        moviesDataSource = MoviesFakeDataSource(
            fullMovies = mutableMapOf(Pair(fakeMovie.id, fakeMovie))
        )
        moviesRepository = DefaultMoviesRepository(moviesDataSource)
        viewModel = MovieViewModel(moviesRepository)
    }

    @Test
    fun `when can't load the Movie, should show a error`() = runTest {
        // Given
        moviesDataSource.isOffline = true

        // When
        viewModel.start(0)

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        Truth.assertThat(resultState.isLoading).isFalse()
        Truth.assertThat(resultState.errorMessage).isEqualTo(MovieStateErrors.ERROR_GETTING_MOVIE)
    }

    @Test
    fun `when Movie is load, should hide loading and error`() = runTest {
        // Given
        val movie = fakeMovie.copy()

        // When
        viewModel.start(movie.id)

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        Truth.assertThat(resultState.isLoading).isFalse()
        Truth.assertThat(resultState.errorMessage).isNull()
    }

    @Test
    fun `when Movie is load, should show its content`() = runTest {
        // Given
        val movie = fakeMovie.copy()

        // When
        viewModel.start(movie.id)

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        Truth.assertThat(resultState.movie).isEqualTo(movie)
    }

    @Test
    fun `when load Movie error is shown, should clear the error`() = runTest {
        // Given
        moviesDataSource.isOffline = true
        viewModel.start(fakeMovie.id)

        // When
        val error = MovieStateErrors.ERROR_GETTING_MOVIE
        viewModel.onErrorShown(error, false)

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        Truth.assertThat(resultState.errorMessage).isNull()
    }

    @Test
    fun `when load Movie error is shown and try again, should load Movie`() = runTest {
        // Given
        val tryAgain = true
        val error = MovieStateErrors.ERROR_GETTING_MOVIE
        moviesDataSource.isOffline = true
        viewModel.start(fakeMovie.id)

        // When
        moviesDataSource.isOffline = false
        viewModel.onErrorShown(error, tryAgain)

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        Truth.assertThat(resultState.movie).isEqualTo(fakeMovie)
    }
}