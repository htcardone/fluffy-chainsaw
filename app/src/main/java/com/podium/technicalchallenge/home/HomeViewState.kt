package com.podium.technicalchallenge.home

import com.podium.technicalchallenge.data.entity.MovieEntity

sealed class HomeViewState {
    object Loading: HomeViewState()
    object Error: HomeViewState()
    data class Content(
        val topMovies: List<MovieEntity>,
        val genres: List<String>
    ): HomeViewState()
}

