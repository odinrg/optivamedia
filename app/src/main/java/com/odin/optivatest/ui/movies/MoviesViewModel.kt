package com.odin.optivatest.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odin.optivatest.data.entities.MovieFav
import com.odin.optivatest.data.entities.MovieModel
import com.odin.optivatest.data.repository.MoviesRepository
import kotlinx.coroutines.launch


class MoviesViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    val movies = repository.getMovies()
    val moviesFav = repository.getFavMovies()

    fun insert(movie: MovieModel) = viewModelScope.launch {
        var movieFav = MovieFav(movie.externalId,true)
        repository.saveMovieFav(movieFav)
    }

    fun delete(movie: MovieModel) = viewModelScope.launch {
        var movieFav = MovieFav(movie.externalId,true)
        repository.deleteMovieFav(movieFav)
    }

}
