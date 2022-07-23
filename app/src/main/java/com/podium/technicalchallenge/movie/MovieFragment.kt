package com.podium.technicalchallenge.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.podium.technicalchallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val args: MovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        val composeView: ComposeView = view.findViewById(R.id.compose_view_movie)

        composeView.setContent {
            MovieScreen(id = args.movieId)
        }

        return view
    }

}