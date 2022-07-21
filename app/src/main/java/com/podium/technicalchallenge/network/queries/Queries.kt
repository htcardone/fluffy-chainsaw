package com.podium.technicalchallenge.network.queries

object Queries {
    fun getMoviesQuery() =
"""
    query GetMoviesQuery {
  movies {
    title
    releaseDate
  }
}
"""
}
