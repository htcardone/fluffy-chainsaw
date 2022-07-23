package com.podium.technicalchallenge.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.podium.technicalchallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val composeView: ComposeView = view.findViewById(R.id.compose_view_home)

        composeView.setContent {
            HomeScreen(
                onMoviesClick = {
                    findNavController()
                        .navigate(HomeFragmentDirections.actionHomeFragmentToMoviesFragment())
                },
                onMovieClick = {

                }
            )
        }

        return view
    }
}