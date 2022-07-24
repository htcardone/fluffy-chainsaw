package com.podium.technicalchallenge.home

import com.podium.technicalchallenge.data.entity.MovieEntity

data class HomeViewState (
    val isLoadingMovies: Boolean = false,
    val isLoadingGenres: Boolean = false,
    val errorMessage: HomeViewStateErrors? = null,
    val topMovies: List<MovieEntity> = listOf(),
    val genres: List<String> = listOf()
)

enum class HomeViewStateErrors {
    ERROR_GETTING_GENRES,
    ERROR_GETTING_TOP_MOVIES
}

