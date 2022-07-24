package com.podium.technicalchallenge.movie

sealed class MovieViewEvent {
    data class OnGenreClicked(val genre: String): MovieViewEvent()
    data class OnErrorShown(val error: MovieStateErrors, val tryAgain: Boolean) : MovieViewEvent()
}
