package com.podium.technicalchallenge.home

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
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val genresRepository: GenresRepository
): ViewModel() {

    private var viewState = HomeViewState(
        isLoadingGenres = true,
        isLoadingMovies = true
    )
    val viewStateLiveData = MutableLiveData(viewState)

    fun start() {
        viewModelScope.launch {
            loadGenres()
        }

        viewModelScope.launch {
            loadTopMovies()
        }
    }

    private suspend fun loadTopMovies() {
        val result = moviesRepository.getTopMovies(5, false)

        when (result) {
            is Result.Success -> {
                val movies = result.data
                viewState = viewState.copy(
                    isLoadingMovies = false,
                    topMovies = movies
                )
            }

            is Result.Error -> {
                viewState = viewState.copy(
                    isLoadingMovies = false,
                    errorMessage = HomeViewStateErrors.ERROR_GETTING_TOP_MOVIES
                )
            }
        }

        viewStateLiveData.value = viewState
    }

    private suspend fun loadGenres() {
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
                    errorMessage = HomeViewStateErrors.ERROR_GETTING_GENRES
                )
            }
        }

        viewStateLiveData.value = viewState
    }

    fun onErrorShow(error: HomeViewStateErrors, tryAgain: Boolean) {
        when (error) {
            HomeViewStateErrors.ERROR_GETTING_GENRES -> {
                viewState = viewState.copy(errorMessage = null)
                if (tryAgain) {
                    viewModelScope.launch {
                        loadGenres()
                    }
                }
            }

            HomeViewStateErrors.ERROR_GETTING_TOP_MOVIES -> {
                viewState = viewState.copy(errorMessage = null)
                if (tryAgain) {
                    viewModelScope.launch {
                        loadTopMovies()
                    }
                }
            }
        }

        viewStateLiveData.value = viewState
    }
}