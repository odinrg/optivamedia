package com.odin.optivatest.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.odin.optivatest.data.entities.AttachmentsTypeConverter
import com.odin.optivatest.data.entities.MovieFav
import com.odin.optivatest.data.entities.MovieModel

@Database(entities = [MovieModel::class, MovieFav::class], version = 1, exportSchema = false)
@TypeConverters(AttachmentsTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MoviesDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "moviesdb")
                .fallbackToDestructiveMigration()
                .build()
    }

}