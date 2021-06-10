package com.odin.optivatest.data.remote

import com.odin.optivatest.data.entities.MovieModel
import com.odin.optivatest.data.entities.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesService {
    @GET("GetUnifiedList?client=json&statuses=published&definitions=SD;HD;4K&external_category_id=SED_3880&filter_empty_categories=true")
    suspend fun getAllMovies() : Response<MoviesResponse>

    @GET("GetVideo?client=json")
    suspend fun getMovie(@Query("external_id") id: String): Response<MovieModel>
}