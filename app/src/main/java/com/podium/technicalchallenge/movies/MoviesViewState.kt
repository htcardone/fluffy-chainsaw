package com.podium.technicalchallenge.movies

import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.data.network.queries.OrderBy
import com.podium.technicalchallenge.data.network.queries.Sort

data class MoviesViewState(
    val isLoadingMovies: Boolean = false,
    val isLoadingGenres: Boolean = false,
    val errorMessage: MoviesStateErrors? = null,
    val movies: List<MovieEntity> = listOf(),
    val genres: List<String> = listOf(),
    val genre: String? = null,
    val orderBy: String = OrderBy.VOTE_AVERAGE,
    val sort: String = Sort.DESC
)

enum class MoviesStateErrors {
    ERROR_GETTING_GENRES,
    ERROR_GETTING_MOVIES,
}