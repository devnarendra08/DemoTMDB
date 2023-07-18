package io.buildwithnd.demotmdb.ui.listing

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.buildwithnd.demotmdb.data.MovieRepository
import io.buildwithnd.demotmdb.model.Movie
import io.buildwithnd.demotmdb.model.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for ListingActivity
 */
class ListingViewModel @ViewModelInject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    val movieList = MutableLiveData<List<Movie>>()
    val loadingIsShowing = MutableLiveData<Boolean>()
    val showError = MutableLiveData<String>()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            loadingIsShowing.postValue(true)
            movieRepository.fetchTrendingMovies().collect { result ->

                when (result?.status) {
                    Result.Status.SUCCESS -> {
                        result.data?.results?.let { listOfMovies ->
                            movieList.postValue(listOfMovies)
                        }
                        loadingIsShowing.postValue(false)
                    }

                    Result.Status.ERROR -> {
                        result.message?.let {
                            showError.postValue(it)
                        }
                        loadingIsShowing.postValue(false)
                    }

                    Result.Status.LOADING -> {
                        loadingIsShowing.postValue(true)
                    }
                }
            }
        }
    }

    fun searchMovies(query: String) {
        if (query.length > 3) {
            viewModelScope.launch {
                loadingIsShowing.postValue(true)
                movieRepository.searchMovies(query).collect { result ->
                    when (result.status) {
                        Result.Status.SUCCESS -> {
                            result.data?.results?.let { listOfMovies ->
                                movieList.postValue(listOfMovies)
                            }
                            loadingIsShowing.postValue(false)
                        }

                        Result.Status.ERROR -> {
                            result.message?.let {
                                showError.postValue(it)
                            }
                            loadingIsShowing.postValue(false)
                        }

                        Result.Status.LOADING -> {
                            loadingIsShowing.postValue(true)
                        }
                    }
                }
            }
        }
    }

}