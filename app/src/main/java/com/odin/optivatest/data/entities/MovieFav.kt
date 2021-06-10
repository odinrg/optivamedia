package com.odin.optivatest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviesfv")
data class MovieFav(
    @PrimaryKey
    var externalId : String,
    val isFav : Boolean = true
)


