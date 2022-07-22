package com.podium.technicalchallenge.data.network.queries

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

    fun getGenres() =
        """
        query GetGenres {
          genres
        }
        """
}
