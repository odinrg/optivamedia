package com.odin.optivatest.ui.moviedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.odin.optivatest.data.entities.MovieModel
import com.odin.optivatest.data.repository.MoviesRepository
import com.odin.optivatest.utils.Resource

class MovieDetailViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _id = MutableLiveData<String>()

    private val _movie = _id.switchMap { id ->
        repository.getMovie(id)
    }
    val movie: LiveData<Resource<MovieModel>> = _movie


    fun start(id: String) {
        _id.value = id
    }

}
