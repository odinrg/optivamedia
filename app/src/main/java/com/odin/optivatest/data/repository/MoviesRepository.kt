package com.odin.optivatest.data.repository

import androidx.lifecycle.LiveData
import com.odin.optivatest.data.entities.MovieFav
import com.odin.optivatest.data.entities.MovieModel
import com.odin.optivatest.data.local.MoviesDao
import com.odin.optivatest.data.remote.MoviesRemoteDataSource
import com.odin.optivatest.utils.performGetOperation
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesDao
) {

    fun getMovie(id: String) = performGetOperation(
        databaseQuery = { localDataSource.getMovie(id) },
        networkCall = { remoteDataSource.getMovie(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getMovies() = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies() },
        networkCall = { remoteDataSource.getMovies() },
        saveCallResult = { localDataSource.insertAll(it.response) }
    )

    suspend fun saveMovieFav(movie: MovieFav) {
        localDataSource.insertFav(movie)
    }

    suspend fun deleteMovieFav(movie: MovieFav) {
        localDataSource.deleteFav(movie)
    }

    fun getFavMovies() = performGetOperation(
        databaseQuery = { localDataSource.getAllMoviesFav() }
    )





}