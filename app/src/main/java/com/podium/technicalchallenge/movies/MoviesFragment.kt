package com.podium.technicalchallenge.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.movies.iu.MoviesScreen
import com.podium.technicalchallenge.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val args: MoviesFragmentArgs by navArgs()
    private val moviesViewModel: MoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        val composeView: ComposeView = view.findViewById(R.id.compose_view_movies)

        val orderBy = savedInstanceState?.getString(KEY_SAVED_ORDER)
        val sort = savedInstanceState?.getString(KEY_SAVED_SORT)

        composeView.setContent {
            moviesViewModel.viewStateLiveData.observeAsState().value?.let { viewState ->
                MyTheme {
                    MoviesScreen(
                        viewState = viewState,
                        sendEvent = { event ->
                            handleEvent(event)
                        }
                    )
                }
            }
        }

        moviesViewModel.start(15, args.genre, orderBy, sort)

        return view
    }

    private fun handleEvent(event: MoviesViewEvent) {
        when (event) {
            is MoviesViewEvent.OnErrorShown -> {
                moviesViewModel.onErrorShow(event.error, event.tryAgain)
            }

            is MoviesViewEvent.OnMovieClicked -> navigateToMovie(event.movieId)

            is MoviesViewEvent.OnGenreClicked -> moviesViewModel.onGenreClicked(event.genre)

            is MoviesViewEvent.OnSortClicked ->
                moviesViewModel.onSortClicked(event.orderBy, event.sort)

            is MoviesViewEvent.OnViewAllClicked -> {
                // do nothing
            }
        }
    }

    private fun navigateToMovie(movieId: Int) {
        findNavController().navigate(MoviesFragmentDirections
            .actionMoviesFragmentToMovieFragment(movieId))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        moviesViewModel.viewStateLiveData.value?.let {
            outState.putString(KEY_SAVED_ORDER, it.orderBy)
            outState.putString(KEY_SAVED_SORT, it.sort)
        }
    }

    companion object {
        const val KEY_SAVED_ORDER = "KEY_SAVED_ORDER"
        const val KEY_SAVED_SORT = "KEY_SAVED_SORT"
    }
}