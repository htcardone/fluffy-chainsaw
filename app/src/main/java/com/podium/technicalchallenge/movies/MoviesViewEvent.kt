package com.podium.technicalchallenge.movies

sealed class MoviesViewEvent {
    object OnViewAllClicked: MoviesViewEvent()
    data class OnSortClicked(val orderBy: String, val sort: String): MoviesViewEvent()
    data class OnGenreClicked(val genre: String?): MoviesViewEvent()
    data class OnMovieClicked(val movieId: Int): MoviesViewEvent()
    data class OnErrorShown(val error: MoviesStateErrors, val tryAgain: Boolean): MoviesViewEvent()
}
