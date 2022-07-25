package com.podium.technicalchallenge.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.sources.genres.GenresRepository
import com.podium.technicalchallenge.data.sources.movies.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val genresRepository: GenresRepository
): ViewModel() {

    private var moviesLimit = 15

    private var viewState = MoviesViewState(
        isLoadingMovies = true
    )

    val viewStateLiveData = MutableLiveData(viewState)

    fun start(moviesLimit: Int = 5, genre: String? = null, orderBy: String? = null,
              sort: String? = null) {

        this.moviesLimit = moviesLimit
        viewState = viewState.copy(
            genre = genre,
            orderBy = orderBy ?: viewState.orderBy,
            sort = sort ?: viewState.sort
        )

        viewModelScope.launch {
            loadMovies()
        }

        viewModelScope.launch {
            loadGenres()
        }
    }

    fun onGenreClicked(genre: String?) {
        if (genre != viewState.genre) {
            viewState = viewState.copy(genre = genre)
            viewModelScope.launch {
                loadMovies()
            }
        }
    }

    fun onSortClicked(orderBy: String, sort: String) {
        if (orderBy != viewState.orderBy || sort != viewState.sort) {
            viewState = viewState.copy(orderBy = orderBy, sort = sort)
            viewModelScope.launch {
                loadMovies()
            }
        }
    }

    fun onErrorShow(error: MoviesStateErrors, tryAgain: Boolean) {
        viewState = viewState.copy(errorMessage = null)
        viewStateLiveData.value = viewState

        when (error) {
            MoviesStateErrors.ERROR_GETTING_GENRES -> {
                if (tryAgain) {
                    viewModelScope.launch {
                        loadGenres()
                    }
                }
            }

            MoviesStateErrors.ERROR_GETTING_MOVIES -> {
                if (tryAgain) {
                    viewModelScope.launch {
                        loadMovies()
                    }
                }
            }
        }
    }

    private suspend fun loadMovies() {
        viewState = viewState.copy(isLoadingMovies = true)
        viewStateLiveData.value = viewState

        val result = moviesRepository.getMovies(viewState.genre, viewState.orderBy,
            viewState.sort, moviesLimit)

        when (result) {
            is Result.Success -> {
                val movies = result.data

                viewState = if (movies.isEmpty()) {
                    viewState.copy(
                        isLoadingMovies = false,
                        errorMessage = MoviesStateErrors.ERROR_GETTING_MOVIES
                    )
                } else {
                    viewState.copy(
                        isLoadingMovies = false,
                        movies = movies
                    )
                }
            }

            is Result.Error -> {
                viewState = viewState.copy(
                    isLoadingMovies = false,
                    errorMessage = MoviesStateErrors.ERROR_GETTING_MOVIES
                )
            }
        }

        viewStateLiveData.value = viewState
    }

    private suspend fun loadGenres() {
        viewState = viewState.copy(isLoadingGenres = true)
        viewStateLiveData.value = viewState

        val result = genresRepository.getAllGenres(false)

        when (result) {
            is Result.Success -> {
                val genres = result.data.sorted()
                viewState = viewState.copy(
                    isLoadingGenres = false,
                    genres = genres
                )
            }

            is Result.Error -> {
                viewState = viewState.copy(
                    isLoadingGenres = false,
                    errorMessage = MoviesStateErrors.ERROR_GETTING_GENRES
                )
            }
        }

        viewStateLiveData.value = viewState
    }
}