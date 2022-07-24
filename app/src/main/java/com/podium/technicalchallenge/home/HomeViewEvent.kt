package com.podium.technicalchallenge.home

sealed class HomeViewEvent {
    object OnViewAllClicked: HomeViewEvent()
    data class OnMovieClicked(val movieId: Int): HomeViewEvent()
    data class OnGenreClicked(val genre: String): HomeViewEvent()
    data class OnErrorShown(val error: HomeViewStateErrors, val tryAgain: Boolean): HomeViewEvent()
}
