package com.podium.technicalchallenge.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.podium.technicalchallenge.MainDispatcherRule
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.data.sources.genres.DefaultGenresRepository
import com.podium.technicalchallenge.data.sources.genres.GenresFakeDataSource
import com.podium.technicalchallenge.data.sources.genres.GenresRepository
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
class MoviesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var moviesDataSource: MoviesFakeDataSource
    private lateinit var genresDataSource: GenresFakeDataSource
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var genresRepository: GenresRepository
    private lateinit var viewModel: MoviesViewModel

    private val genresFakes = listOf("Action", "Crime", "Comedy")
    private val moviesFakes = listOf(
        MovieEntity(0, "Movie 0", "", 5f, null),
        MovieEntity(1, "Movie 1", "", 6f, null),
        MovieEntity(2, "Movie 2", "", 7f, null),
        MovieEntity(3, "Movie 3", "", 8f, null),
        MovieEntity(4, "Movie 4", "", 9f, null)
    )

    @Before
    fun setup() {
        moviesDataSource = MoviesFakeDataSource(moviesFakes.associateBy { it.id }.toMutableMap())
        genresDataSource = GenresFakeDataSource(genresFakes.toMutableList())
        moviesRepository = DefaultMoviesRepository(moviesDataSource)
        genresRepository = DefaultGenresRepository(genresDataSource)
        viewModel = MoviesViewModel(moviesRepository, genresRepository)
    }

    @Test
    fun `when can't load the Movies, should hide loading and show a error`() = runTest {
        // Given
        moviesDataSource.isOffline = true

        // When
        viewModel.start()

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.isLoadingMovies).isFalse()
        assertThat(resultState.isLoadingGenres).isFalse()
        assertThat(resultState.errorMessage).isEqualTo(MoviesStateErrors.ERROR_GETTING_MOVIES)
    }

    @Test
    fun `when can't load the Genres, should hide loading and show a error`() = runTest {
        // Given
        genresDataSource.isOffline = true

        // When
        viewModel.start()

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.isLoadingMovies).isFalse()
        assertThat(resultState.isLoadingGenres).isFalse()
        assertThat(resultState.errorMessage).isEqualTo(MoviesStateErrors.ERROR_GETTING_GENRES)
    }

    @Test
    fun `when Movies are load, should hide loading and error`() = runTest {
        // When
        viewModel.start()

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.isLoadingMovies).isFalse()
        assertThat(resultState.errorMessage).isNotEqualTo(MoviesStateErrors.ERROR_GETTING_MOVIES)
    }

    @Test
    fun `when Genres are load, should hide loading and error`() = runTest {
        // When
        viewModel.start()

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.isLoadingMovies).isFalse()
        assertThat(resultState.errorMessage).isNotEqualTo(MoviesStateErrors.ERROR_GETTING_GENRES)
    }


    @Test
    fun `when Movies are load, should show list`() = runTest {
        // When
        viewModel.start()

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.movies).isEqualTo(moviesFakes)
    }

    @Test
    fun `when Genres are load, should show list in alphabetical order`() = runTest {
        // When
        viewModel.start()

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.genres).isEqualTo(genresFakes.sorted())
    }

    @Test
    fun `when load Movies error is shown, should clear the error`() = runTest {
        // Given
        moviesDataSource.isOffline = true
        viewModel.start()

        // When
        val error = MoviesStateErrors.ERROR_GETTING_MOVIES
        viewModel.onErrorShow(error, false)

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.errorMessage).isNull()
    }

    @Test
    fun `when load Genres error is shown, should clear the error`() = runTest {
        // Given
        genresDataSource.isOffline = true
        viewModel.start()

        // When
        val error = MoviesStateErrors.ERROR_GETTING_GENRES
        viewModel.onErrorShow(error, false)

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.errorMessage).isNull()
    }

    @Test
    fun `when load Movies error is shown and try again, should load Movies`() = runTest {
        // Given
        val error = MoviesStateErrors.ERROR_GETTING_MOVIES
        val tryAgain = true
        moviesDataSource.isOffline = true

        // When
        viewModel.start()
        moviesDataSource.isOffline = false
        viewModel.onErrorShow(error, tryAgain)

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.movies).isEqualTo(moviesFakes)
    }

    @Test
    fun `when load Genres error is shown and try again, should load Genres`() = runTest {
        // Given
        val error = MoviesStateErrors.ERROR_GETTING_GENRES
        val tryAgain = true
        genresDataSource.isOffline = true

        // When
        viewModel.start()
        genresDataSource.isOffline = false
        viewModel.onErrorShow(error, tryAgain)

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.genres).isEqualTo(genresFakes.sorted())
    }

    @Test
    fun `when change Order and Sort, should update view state`() = runTest {
        // Given
        viewModel.start()

        // When
        viewModel.onSortClicked("test order", "test sort")

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.orderBy).isEqualTo("test order")
        assertThat(resultState.sort).isEqualTo("test sort")
    }

    @Test
    fun `when change Genre, should update view state`() = runTest {
        // Given
        viewModel.start()

        // When
        viewModel.onGenreClicked("test genre")

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.genre).isEqualTo("test genre")
    }

    @Test
    fun `when Genre is null, should update view state`() = runTest {
        // Given
        viewModel.start()

        // When
        viewModel.onGenreClicked(null)

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.genre).isNull()
    }

    @Test
    fun `when start without Order and Sort, should keep actual Order and Sort`() = runTest {
        // Given
        viewModel.start()
        viewModel.onSortClicked("first order", "first sort")

        // When
        viewModel.start()

        // Then
        val resultState = viewModel.viewStateLiveData.value!!
        assertThat(resultState.orderBy).isEqualTo("first order")
        assertThat(resultState.sort).isEqualTo("first sort")
    }
}