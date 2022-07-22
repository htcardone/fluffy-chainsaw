package com.podium.technicalchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.network.queries.OrderBy
import com.podium.technicalchallenge.data.network.queries.Sort
import com.podium.technicalchallenge.data.sources.genres.GenresRepository
import com.podium.technicalchallenge.data.sources.movies.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val genresRepository: GenresRepository,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    fun getMovies() {
        /*viewModelScope.launch() {
            val result = genresRepository.getAllGenres(false)
            when (result) {
                is Result.Success -> {
                    val genres = result.data
                    Log.d(LOG_TAG, "genres=$genres")
                }

                is Result.Error -> {
                    Log.e(LOG_TAG, "error while getting the genres", result.exception)
                }
            }
        }*/

        /*viewModelScope.launch() {
            val result = moviesRepository.getTopMovies(5)
            when (result) {
                is Result.Success -> {
                    val movies = result.data
                    Log.d(LOG_TAG, "movies=$movies")
                }

                is Result.Error -> {
                    Log.e(LOG_TAG, "error while getting the top movies", result.exception)
                }
            }
        }*/

        viewModelScope.launch() {
            val result = moviesRepository.getMoviesByGenre("Action", OrderBy.TITLE, Sort.ASC, 10)
            when (result) {
                is Result.Success -> {
                    val movies = result.data
                    Log.d(LOG_TAG, "movies=$movies")
                }

                is Result.Error -> {
                    Log.e(LOG_TAG, "error while getting the top movies", result.exception)
                }
            }
        }

        /*viewModelScope.launch() {
            val result = moviesRepository.getMovieDetails(7759960)
            when (result) {
                is Result.Success -> {
                    val movie = result.data
                    Log.d(LOG_TAG, "movie=$movie")
                }

                is Result.Error -> {
                    Log.e(LOG_TAG, "error while getting the movie", result.exception)
                }
            }
        }*/
    }

    companion object {
        const val LOG_TAG = "DemoViewModel"
    }
}
