package com.inhyuck.examples.kotlinnews.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inhyuck.examples.kotlinnews.db.entity.Feed

@Dao
abstract class FeedDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert( feeds: List<Feed>?)

    @Query("SELECT * FROM feed")
    abstract fun loadAll(): LiveData<List<Feed>>

    @Query("SELECT * FROM feed where id=:feedID")
    abstract fun lookupFeed(feedID: String): LiveData<Feed>

    @Query("DELETE FROM feed")
    abstract fun deleteAll()
}