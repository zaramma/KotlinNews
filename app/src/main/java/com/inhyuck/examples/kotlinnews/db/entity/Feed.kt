package com.inhyuck.examples.kotlinnews.db.entity

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(primaryKeys = ["id"])
@TypeConverters(DataConverter::class)
data class Feed (
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("thumbnail_url")
    var thumbnailUrl: String?,

    val media: Media?,

    @field:SerializedName("selftext")
    var selfText: String?,

    @field:SerializedName("hidden")
    val hidden : Boolean?,

    @field:SerializedName("author_fullname")
    val authorFullName: String?
){
    init {
        if (selfText.isNullOrEmpty()){
            selfText = getAnyContents()
        }
    }

    fun getAnyContents(): String? {
        var ret = selfText
        if (ret.isNullOrEmpty()) {
            ret = title
        }
        if (ret.isNullOrEmpty()) {
            ret = authorFullName
        }
        return ret
    }
}

data class Media(
    val oembed: Oembed?
)

data class Oembed(
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?
)

class DataConverter {

    @TypeConverter
    fun fromMedia(value: Media?): String? {
        if (value == null){
            return null
        }

        val type = object : TypeToken<Media>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toCountryLangList(value: String?): Media? {
        if (value == null){
            return null
        }

        val type = object : TypeToken<Media>() {}.type
        return Gson().fromJson(value, type)
    }
}