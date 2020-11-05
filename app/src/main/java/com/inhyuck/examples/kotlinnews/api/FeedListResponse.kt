package com.inhyuck.examples.kotlinnews.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.inhyuck.examples.kotlinnews.db.entity.Feed

data class FeedListResponse (
    val kind: String?,
    val data: Data?
)

data class Data(
    val dist: Int?,
    val children: ArrayList<Child>?,
    val after: String?,
    val before: String?
)

@Entity
data class Child(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val kind: String,
    @field:SerializedName("data")
    val feed: Feed?
)