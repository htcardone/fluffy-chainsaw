package com.podium.technicalchallenge.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun start() {
        viewModelScope.launch {
            val genres = genresRepository.getAllGenres(false)
            val topMovies = moviesRepository.getTopMovies(5, false)
        }
    }
}