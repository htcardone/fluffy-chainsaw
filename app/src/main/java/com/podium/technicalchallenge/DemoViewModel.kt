package com.podium.technicalchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.data.Result
import com.podium.technicalchallenge.data.sources.genres.GenresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val genresRepository: GenresRepository
) : ViewModel() {

    fun getMovies() {
        viewModelScope.launch() {
            val result = genresRepository.getAllGenres(false)
            when (result) {
                is Result.Success -> {
                    val genres = result.data
                    Log.d(LOG_TAG, "genres=$genres")
                }

                is Result.Error -> {
                    Log.e(LOG_TAG, "error while getting the genres", result.exception)
                }
            }
        }
    }

    companion object {
        const val LOG_TAG = "DemoViewModel"
    }
}
