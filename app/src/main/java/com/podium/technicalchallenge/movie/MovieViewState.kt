package com.podium.technicalchallenge.movie

import com.podium.technicalchallenge.data.entity.MovieFullEntity

data class MovieViewState(
    val isLoading: Boolean = false,
    val errorMessage: MovieStateErrors? = null,
    val movie: MovieFullEntity? = null
)

enum class MovieStateErrors {
    ERROR_GETTING_MOVIE,
}