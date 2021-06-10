package com.odin.optivatest.data.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class AttachmentsTypeConverter {

    var gson = Gson()
    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Attachments> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Attachments?>?>() {}.type
        return gson.fromJson<List<Attachments>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Attachments?>?): String {
        return gson.toJson(someObjects)
    }
}