package io.buildwithnd.demotmdb.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import io.buildwithnd.demotmdb.data.MovieRepository
import io.buildwithnd.demotmdb.model.MovieDesc
import io.buildwithnd.demotmdb.model.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for Movie details screen
 */
class DetailsViewModel @ViewModelInject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    val loadingLiveData = MutableLiveData<Boolean>()
    val showErrorLiveData = MutableLiveData<String>()
    val movieDetailLiveData = MutableLiveData<MovieDesc>()

    fun fetchDetails(id: Int) {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            movieRepository.fetchMovie(id).collect {
                when (it.status) {
                    Result.Status.SUCCESS -> {
                        it.data?.let {
                            movieDetailLiveData.postValue(it)
                        }
                        loadingLiveData.postValue(false)
                    }

                    Result.Status.ERROR -> {
                        it.message?.let {
                            showErrorLiveData.postValue(it)
                        }
                        loadingLiveData.postValue(false)
                    }

                    Result.Status.LOADING -> {
                        loadingLiveData.postValue(true)
                    }
                }
            }
        }
    }
}