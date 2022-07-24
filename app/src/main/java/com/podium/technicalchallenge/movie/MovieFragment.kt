package com.podium.technicalchallenge.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.movie.ui.MovieScreen
import com.podium.technicalchallenge.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val args: MovieFragmentArgs by navArgs()
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        val composeView: ComposeView = view.findViewById(R.id.compose_view_movie)

        composeView.setContent {
            movieViewModel.viewStateLiveData.observeAsState().value?.let { viewState ->
                MyTheme {
                    MovieScreen(
                        viewState = viewState,
                        sendEvent = { event ->
                            handleEvent(event)
                        }
                    )
                }
            }
        }

        movieViewModel.start(args.movieId)

        return view
    }

    private fun handleEvent(event: MovieViewEvent) {
        when (event) {
            is MovieViewEvent.OnGenreClicked -> navigateToGenre(event.genre)
            is MovieViewEvent.OnErrorShown -> movieViewModel.onErrorShown(event.error, event.tryAgain)
        }
    }

    private fun navigateToGenre(genre: String) {
        findNavController().navigate(MovieFragmentDirections
            .actionMovieFragmentToMoviesFragment(genre))
    }
}
