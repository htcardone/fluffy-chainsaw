package com.podium.technicalchallenge.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.data.network.queries.OrderBy
import com.podium.technicalchallenge.data.network.queries.Sort
import com.podium.technicalchallenge.home.ui.HomeScreen
import com.podium.technicalchallenge.movies.MoviesViewEvent
import com.podium.technicalchallenge.movies.MoviesViewModel
import com.podium.technicalchallenge.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val composeView: ComposeView = view.findViewById(R.id.compose_view_home)

        composeView.setContent {
            moviesViewModel.viewStateLiveData.observeAsState().value?.let { viewState ->
                MyTheme {
                    HomeScreen(
                        viewState = viewState,
                        sendEvent = { event ->
                            handleEvent(event)
                        }
                    )
                }
            }
        }

        moviesViewModel.start(5, orderBy = OrderBy.VOTE_AVERAGE, sort = Sort.DESC)

        return view
    }

    /*
    Some events (like navigation and analytics) doesn't need to be handled by the ViewModel,
    since they don't deal with state. This way, we avoid the exposure of one-time events from the
    ViewModel.
     */
    private fun handleEvent(event: MoviesViewEvent) {
        when (event) {
            is MoviesViewEvent.OnErrorShown -> {
                moviesViewModel.onErrorShow(event.error, event.tryAgain)
            }

            is MoviesViewEvent.OnGenreClicked -> navigateToGenre(event.genre)

            is MoviesViewEvent.OnMovieClicked -> navigateToMovie(event.movieId)

            is MoviesViewEvent.OnViewAllClicked -> navigateToAllMovies()

            is MoviesViewEvent.OnSortClicked -> {
                // do nothing
            }
        }
    }

    private fun navigateToAllMovies() {
        findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToMoviesFragment(null))
    }

    private fun navigateToMovie(movieId: Int) {
        findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToMovieFragment(movieId))
    }

    private fun navigateToGenre(genre: String?) {
        genre?.let {
            findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToMoviesFragment(it))
        }
    }
}