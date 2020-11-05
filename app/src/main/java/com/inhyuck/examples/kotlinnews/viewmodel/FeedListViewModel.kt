package com.inhyuck.examples.kotlinnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inhyuck.examples.kotlinnews.api.FeedListResponse
import com.inhyuck.examples.kotlinnews.api.RadditService
import com.inhyuck.examples.kotlinnews.db.AppDB
import com.inhyuck.examples.kotlinnews.db.entity.Feed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class FeedListViewModel(
    private val radditService: RadditService,
    private val db: AppDB
) : ViewModel(){
    var feedList : LiveData<List<Feed>>

    init {
        fetchUpdate()
        feedList = db.feedDao().loadAll()
    }

    var foundError = MutableLiveData<String?>()

    fun fetchUpdate(){
        //TODO: set loading status

        radditService.getFeedList().enqueue(
            object : Callback<FeedListResponse>{
                override fun onFailure(call: Call<FeedListResponse>, t: Throwable) {
                    //TODO: handle failure
                }

                override fun onResponse(
                    call: Call<FeedListResponse>,
                    response: Response<FeedListResponse>
                ) {
                    //parse data received
                    if (response.body() != null && response.body()?.data != null){
                        val feeds = ArrayList<Feed>()
                        val children = response.body()?.data?.children
                        children?.let { it->
                            for (child in it){
                                if (child.feed != null){
                                    child.feed.thumbnailUrl = child.feed.media?.oembed?.thumbnailUrl
                                    feeds.add(child.feed)
                                }
                            }
                        }

                        //reset feeds
                        Executors.newSingleThreadExecutor().execute {
                            db.feedDao().deleteAll()
                            db.feedDao().insert(feeds)
                        }
                    }
                }
            })
        //TODO: set loading status
    }
}

object FeedListVMFactory : ViewModelProvider.Factory {
    lateinit var appDB: AppDB

    fun setDB(db: AppDB) : FeedListVMFactory{
        appDB = db
        return this
    }

    private fun getService() :RadditService {
        return Retrofit.Builder()
            .baseUrl("https://www.reddit.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RadditService::class.java)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedListViewModel(getService(), appDB) as T
    }
}
