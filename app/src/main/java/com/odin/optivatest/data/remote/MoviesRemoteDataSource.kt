package com.odin.optivatest.data.remote

import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val moviesService: MoviesService
): BaseDataSource() {

    suspend fun getMovies() = getResult { moviesService.getAllMovies() }
    suspend fun getMovie(id: String) = getResult { moviesService.getMovie(id) }

}
