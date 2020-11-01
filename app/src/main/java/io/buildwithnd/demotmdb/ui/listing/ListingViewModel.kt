package io.buildwithnd.demotmdb.ui.listing

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.buildwithnd.demotmdb.data.MovieRepository
import io.buildwithnd.demotmdb.model.Result
import io.buildwithnd.demotmdb.model.TrendingMovieResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

/**
 * ViewModel for ListingActivity
 */
class ListingViewModel @ViewModelInject constructor(private val movieRepository: MovieRepository) :
        ViewModel() {

    private val _movieList = MutableLiveData<Result<TrendingMovieResponse>>()
    val movieList = _movieList

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            movieRepository.fetchTrendingMovies().collect {
                _movieList.value = it
            }
        }
    }
}