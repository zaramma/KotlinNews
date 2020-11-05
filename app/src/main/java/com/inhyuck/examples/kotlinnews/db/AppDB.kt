package com.inhyuck.examples.kotlinnews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.inhyuck.examples.kotlinnews.db.dao.FeedDao
import com.inhyuck.examples.kotlinnews.db.entity.Feed

@Database(entities = [Feed::class], version = 1, exportSchema = false)
abstract class AppDB: RoomDatabase() {
    abstract fun feedDao(): FeedDao

    companion object {
        @Volatile private var INSTANCE: AppDB? = null

        fun getInstance(context: Context): AppDB = INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            AppDB::class.java, "KotlinNews.db").build()
    }
}