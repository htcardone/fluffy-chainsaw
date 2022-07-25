package com.podium.technicalchallenge.data.network.queries

object Queries {

    fun getMoviesQuery(orderBy: String, sort: String, limit: Int) =
    """
        query GetMoviesQuery {
          movies(limit: $limit, orderBy:"$orderBy", sort: $sort) {
            id
            title
            voteAverage
            releaseDate
            posterPath
          }
    }
    """

    fun getMoviesByGenreQuery(genre: String, orderBy: String, sort: String, limit: Int) =
        """
        query GetMoviesQuery {
          movies(genre: "$genre", limit: $limit, orderBy:"$orderBy", sort: $sort) {
            id
            title
            voteAverage
            releaseDate
            posterPath
          }
    }
    """

    fun getMovieDetails(movieId: Int) =
    """
        query GetMovieQuery {
          movie(id: $movieId) {
            id
            title
            releaseDate
            genres
            voteAverage
            voteCount
            popularity
            releaseDate
            posterPath
            overview
            runtime
            cast {
              name
              profilePath
            }
            director {
              id
              name
            }
          }
        }
    """

    fun getGenres() =
    """
        query GetGenres {
          genres
        }
    """
}
