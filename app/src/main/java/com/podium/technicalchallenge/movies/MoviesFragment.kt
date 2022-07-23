package com.podium.technicalchallenge.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.podium.technicalchallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        val composeView: ComposeView = view.findViewById(R.id.compose_view_movies)

        composeView.setContent {
            MoviesScreen(onMovieClicked = { movieId ->
                findNavController()
                    .navigate(MoviesFragmentDirections.actionMoviesFragmentToMovieFragment(movieId))
            })
        }

        return view
    }

}