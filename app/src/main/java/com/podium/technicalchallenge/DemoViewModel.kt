package com.podium.technicalchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DemoViewModel : ViewModel() {
    val TAG = "DemoViewModel"

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                Repo.getInstance().getMovies()
            } catch (e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<List<MovieEntity>?> -> {
                    Log.d(TAG, "movies= " + result.data)
                }
                else -> {
                    Log.e(TAG, "movies= " + result)
                }
            }
        }
    }
}
