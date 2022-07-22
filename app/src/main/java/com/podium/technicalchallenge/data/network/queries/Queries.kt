package com.podium.technicalchallenge.data.network.queries

object Queries {

    fun getMoviesQuery(orderBy: OrderBy, sort: Sort, limit: Int) =
    """
        query GetMoviesQuery {
          movies(limit: $limit, orderBy:"${orderBy.field}", sort: $sort) {
            id
            title
            voteAverage
            releaseDate
            posterPath
          }
    }
    """

    fun getMoviesByGenreQuery(genre: String, orderBy: OrderBy, sort: Sort, limit: Int) =
        """
        query GetMoviesQuery {
          movies(genre: "$genre", limit: $limit, orderBy:"${orderBy.field}", sort: $sort) {
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
            popularity
            releaseDate
            posterPath
            overview
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
