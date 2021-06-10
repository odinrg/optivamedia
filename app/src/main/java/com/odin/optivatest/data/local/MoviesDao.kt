package com.odin.optivatest.data.local


import androidx.lifecycle.LiveData
import androidx.room.*
import com.odin.optivatest.data.entities.MovieFav
import com.odin.optivatest.data.entities.MovieModel

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getAllMovies() : LiveData<List<MovieModel>>

    @Query("SELECT * FROM movies WHERE externalId = :id")
    fun getMovie(id: String): LiveData<MovieModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieModels: List<MovieModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieModel: MovieModel)

    //Favorites
    @Query("SELECT EXISTS(SELECT * FROM moviesfv WHERE externalId = :id)")
    fun isRowIsExist(id : String) : LiveData<Boolean>

    @Query("SELECT * FROM moviesfv")
    fun getAllMoviesFav(): LiveData<List<MovieFav>>

    /*@Query("SELECT * FROM movies INNER JOIN moviesfv ON externalId")
    fun getAllMoviesFav2(): LiveData<List<MovieModel>>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(movie: MovieFav)

    @Delete
    suspend fun deleteFav(movie: MovieFav)



}