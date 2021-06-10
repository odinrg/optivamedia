package com.odin.optivatest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
data class MovieModel (

	val year : Int,
	@PrimaryKey
	val id : Int,
	val name : String,
	val attachments : List<Attachments>,
	val reviewerRating : String,
	val description : String,
	val externalId : String,
	var isFav : Boolean

)