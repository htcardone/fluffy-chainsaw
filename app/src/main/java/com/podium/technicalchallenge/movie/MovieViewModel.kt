package com.podium.technicalchallenge.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.sources.movies.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {

    private var movieId: Int? = null
    private var viewState = MovieViewState(
        isLoading = true
    )

    val viewStateLiveData = MutableLiveData(viewState)

    fun start(movieId: Int) {
        this.movieId = movieId

        viewModelScope.launch {
            loadMovie(movieId)
        }
    }

    fun onErrorShown(error: MovieStateErrors, tryAgain: Boolean) {
        viewState = viewState.copy(errorMessage = null)
        viewStateLiveData.value = viewState

        when (error) {
            MovieStateErrors.ERROR_GETTING_MOVIE -> {
                if (tryAgain) {
                    movieId ?.let {
                        viewModelScope.launch {
                            loadMovie(it)
                        }
                    } ?: run {
                        viewState = viewState.copy(
                            errorMessage = MovieStateErrors.ERROR_GETTING_MOVIE
                        )
                        viewStateLiveData.value = viewState
                    }
                }
            }
        }
    }

    private suspend fun loadMovie(movieId: Int) {
        viewState = viewState.copy(isLoading = true)
        viewStateLiveData.value = viewState

        val result = moviesRepository.getMovieDetails(movieId)

        when (result) {
            is Result.Success -> {
                val movie = result.data
                viewState = viewState.copy(
                    isLoading = false,
                    movie = movie
                )
            }

            is Result.Error -> {
                viewState = viewState.copy(
                    isLoading = false,
                    errorMessage = MovieStateErrors.ERROR_GETTING_MOVIE
                )
            }
        }

        viewStateLiveData.value = viewState
    }
}