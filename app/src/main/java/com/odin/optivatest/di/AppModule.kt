package com.odin.optivatest.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.odin.optivatest.data.local.AppDatabase
import com.odin.optivatest.data.local.MoviesDao
import com.odin.optivatest.data.remote.MoviesRemoteDataSource
import com.odin.optivatest.data.remote.MoviesService
import com.odin.optivatest.data.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://smarttv.orangetv.orange.es/stv/api/rtv/v1/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService = retrofit.create(MoviesService::class.java)

    @Provides
    fun provideMovieRemoteDataSource(moviesService: MoviesService) = MoviesRemoteDataSource(moviesService)

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @Provides
    fun provideRepository(remoteDataSource: MoviesRemoteDataSource,
                          localDataSource: MoviesDao) =
        MoviesRepository(remoteDataSource, localDataSource)
}